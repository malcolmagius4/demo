package com.example.demo.service

import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.enum.TransactionType
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Player
import com.example.demo.common.model.Transaction
import com.example.demo.infra.repository.PlayerRepository
import com.example.demo.infra.repository.TransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class TransactionService(val transactionRepository: TransactionRepository,
                         val playerService: PlayerService,
                         val playerRepository: PlayerRepository) {

    fun incomingTransaction (amount: BigDecimal, player: Player): Transaction {
        val incomingTransaction: Transaction = transactionRepository.save(
            Transaction(amount = amount, type = TransactionType.IN, player = player))

        player.walletBalance += amount
        player.totalWinnings += amount
        playerRepository.save(player)

        return incomingTransaction
    }

    fun outgoingTransaction (amount: BigDecimal, player: Player): Transaction {
        val outgoingTransaction: Transaction = transactionRepository.save(
            Transaction(amount = amount, type = TransactionType.OUT, player = player))

        player.walletBalance -= amount
        playerRepository.save(player)

        return outgoingTransaction
    }

    fun getTransactions(playerUuid: UUID): List<Transaction> {
        if (!playerService.playerExists(playerUuid)) {
            throw throw BusinessRuntimeException(ErrorCode.PLAYER_UUID_NOT_FOUND, HttpStatus.NOT_FOUND.value())
        }

        return transactionRepository.findByPlayer_PlayerUuid(playerUuid)
    }

}