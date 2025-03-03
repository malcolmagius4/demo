package com.example.demo.service

import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Player
import com.example.demo.infra.repository.PlayerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import java.util.Objects.nonNull

@Service
class PlayerService(val playerRepository: PlayerRepository) {

    fun createPlayer(name: String, surname: String, username: String): Player {

        if (nonNull(playerRepository.findByUsername(username))) {
            throw BusinessRuntimeException(ErrorCode.USERNAME_TAKEN, HttpStatus.BAD_REQUEST.value())
        }

        val newPlayer = Player(name = name, surname = surname, username = username)

        return playerRepository.save(newPlayer)
    }

    fun getPlayerWalletBalance(playerUuid: UUID): BigDecimal {
        val player: Player = getPlayer(playerUuid)

        return player.walletBalance
    }

    fun getPlayer(playerUuid: UUID): Player {
        return playerRepository.findByPlayerUuid(playerUuid)
            ?: throw BusinessRuntimeException(ErrorCode.PLAYER_UUID_NOT_FOUND, HttpStatus.NOT_FOUND.value())
    }

    fun playerExists(playerUuid: UUID): Boolean {
        return playerRepository.existsPlayerByPlayerUuid(playerUuid)
    }

    fun getWinningsLeaderboard(): List<Player> {
        return playerRepository.findTop10ByOrderByTotalWinningsDesc()
    }

}