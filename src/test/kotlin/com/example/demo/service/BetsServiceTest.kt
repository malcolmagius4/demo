package com.example.demo.service

import com.example.demo.common.dto.BetDto
import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Bet
import com.example.demo.common.model.Player
import com.example.demo.infra.repository.BetRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class BetsServiceTest {

    @Mock
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var betRepository: BetRepository

    @Mock
    private lateinit var gameService: GameService

    @Mock
    private lateinit var transactionService: TransactionService

    @InjectMocks
    private lateinit var betsService: BetsService

    private val playerUuid = UUID.randomUUID()

    @Test
    fun getBets_should_return_bets_when_player_exists() {
        //given
        val mockBets = listOf(
            Bet(player = mock(), stakeAmount = BigDecimal("10"), winAmount = BigDecimal.ZERO,
                betValue = 5, actualValue = 3)
        )

        whenever(playerService.playerExists(playerUuid)).thenReturn(true)
        whenever(betRepository.findByPlayer_PlayerUuid(playerUuid)).thenReturn(mockBets)

        //when
        val result = betsService.getBets(playerUuid)

        //then
        assertEquals(mockBets, result)
        verify(playerService).playerExists(playerUuid)
        verify(betRepository).findByPlayer_PlayerUuid(playerUuid)
    }

    @Test
    fun getBets_should_throw_exception_when_player_does_not_exist() {
        //given
        whenever(playerService.playerExists(playerUuid)).thenReturn(false)

        //when
        val exception = assertThrows<BusinessRuntimeException> {
            betsService.getBets(playerUuid)
        }

        //then
        assertEquals(ErrorCode.PLAYER_UUID_NOT_FOUND, exception.errorCode)
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.httpStatus)
    }

    @Test
    fun placeBet_should_deduct_balance_create_bet_and_process_winnings() {
        //given
        val player = Player(username = "testUser", walletBalance = BigDecimal("100"), name = "", surname = "")
        val stakeAmount = BigDecimal("10")
        val betValue = 5
        val betResult = BetDto(stakeAmount, BigDecimal("50"), betValue, 3)
        val dummyBet = Bet(player = player, stakeAmount = stakeAmount,
            winAmount = BigDecimal("50"), betValue = betValue, actualValue = 3)

        whenever(playerService.getPlayer(playerUuid)).thenReturn(player)
        whenever(gameService.playNumbersGame(betValue, stakeAmount)).thenReturn(betResult)
        whenever(betRepository.save(dummyBet)).thenReturn(dummyBet)

        //when
        val result = betsService.placeBet(playerUuid, stakeAmount, betValue)

        //then
        assertEquals(stakeAmount, result.stakeAmount)
        assertEquals(BigDecimal("50"), result.winAmount)
        assertEquals(betValue, result.betValue)
        assertEquals(3, result.actualValue)

        verify(transactionService).outgoingTransaction(stakeAmount, player)
        verify(betRepository).save(any())
        verify(transactionService).incomingTransaction(betResult.winAmount, player)
    }

    @Test
    fun placeBet_should_throw_exception_when_player_has_insufficient_balance() {
        val player = Player(username = "testUser", walletBalance = BigDecimal("5"), name = "", surname = "")
        val stakeAmount = BigDecimal("10")
        val betValue = 5

        whenever(playerService.getPlayer(playerUuid)).thenReturn(player)

        val exception = assertThrows<BusinessRuntimeException> {
            betsService.placeBet(playerUuid, stakeAmount, betValue)
        }

        assertEquals(ErrorCode.INSUFFICIENT_WALLET_BALANCE, exception.errorCode)
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.httpStatus)

        verify(transactionService, never()).outgoingTransaction(any(), any())
        verify(betRepository, never()).save(any())
    }
}
