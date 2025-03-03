package com.example.demo.service

import com.example.demo.common.dto.BetDto
import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Bet
import com.example.demo.infra.repository.BetRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class BetsService(
    val playerService: PlayerService,
    val betRepository: BetRepository,
    val gameService: GameService,
    val transactionService: TransactionService
) {

    fun getBets(playerUuid: UUID): List<Bet> {
        if (!playerService.playerExists(playerUuid)) {
            throw throw BusinessRuntimeException(ErrorCode.PLAYER_UUID_NOT_FOUND, HttpStatus.NOT_FOUND.value())
        }

        return betRepository.findByPlayer_PlayerUuid(playerUuid)
    }

    @Transactional
    fun placeBet(playerUuid: UUID, stakeAmount: BigDecimal, betValue: Int): BetDto {
        val player = playerService.getPlayer(playerUuid)

        if (player.walletBalance < stakeAmount) {
            throw BusinessRuntimeException(ErrorCode.INSUFFICIENT_WALLET_BALANCE, HttpStatus.BAD_REQUEST.value())
        }

        transactionService.outgoingTransaction(stakeAmount, player)

        val betResult: BetDto = gameService.playNumbersGame(betValue, stakeAmount)

        val savedBet: Bet = betRepository.save(Bet(player = player, stakeAmount = betResult.stakeAmount,
            winAmount = betResult.winAmount, betValue = betResult.betValue, actualValue = betResult.actualValue))

        if (betResult.winAmount > BigDecimal.ZERO) {
            transactionService.incomingTransaction(betResult.winAmount, player)
        }

        return BetDto(stakeAmount = savedBet.stakeAmount, winAmount = savedBet.winAmount, betValue = savedBet.betValue,
            actualValue = savedBet.actualValue)
    }

}