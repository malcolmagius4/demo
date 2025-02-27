package com.example.demo.service

import com.example.demo.common.enum.ErrorType
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Player
import com.example.demo.infra.repository.PlayerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.Objects.nonNull

@Service
class PlayerService(val playerRepository: PlayerRepository) {

    fun createPlayer(name: String, surname: String, username: String): Player {

        if (nonNull(playerRepository.findByUsername(username))) {
            throw BusinessRuntimeException(ErrorType.USERNAME_TAKEN, HttpStatus.BAD_REQUEST.value())
        }

        val newPlayer = Player(name = name, surname = surname, username = username)

        return playerRepository.save(newPlayer)
    }

}