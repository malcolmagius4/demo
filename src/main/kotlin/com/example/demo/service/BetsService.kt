package com.example.demo.service

import com.example.demo.common.dto.BetDto
import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.enum.TransactionType
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Bet
import com.example.demo.common.model.Transaction
import com.example.demo.infra.repository.BetRepository
import com.example.demo.infra.repository.TransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BetsService(val playerService: PlayerService, val betRepository: BetRepository, val transactionRepository: TransactionRepository) {

    fun getBets(playerUuid: UUID): Set<Bet> {
        val player = playerService.getPlayer(playerUuid)

        return betRepository.findByPlayer(player)
    }


//    fun placeBet(playerUuid: UUID, stakeAmount: Int, betValue: Int): BetDto {
//        validatePlayGameParameters(playerUuid, stakeAmount, betValue)
//
//        val player = playerService.getPlayer(playerUuid)
//
//        val outgoingTransaction = transactionRepository
//            .save(Transaction(amount = betValue, type = TransactionType.OUT, player = player))
//
//        //play game
//
//    }

    private fun validatePlayGameParameters(playerUuid: UUID, stakeAmount: Int, betValue: Int) {
        if (betValue <= 1 || betValue >= 10) {
            throw BusinessRuntimeException(ErrorCode.INVALID_BET_VALUE, HttpStatus.BAD_REQUEST.value())
        }

        if (playerService.getPlayerWalletBalance(playerUuid) < stakeAmount) {
            throw BusinessRuntimeException(ErrorCode.INSUFFICIENT_WALLET_BALANCE, HttpStatus.BAD_REQUEST.value())
        }
    }

}