package com.example.demo.service

import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.util.RandomNumberHelper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class GameServiceTest {

    @Mock
    private lateinit var randomNumberHelper: RandomNumberHelper

    @InjectMocks
    private lateinit var gameService: GameService

    @Test
    fun playNumbersGame_should_throw_exception_when_invalid_bet_value() {
        //given
        val invalidBetValue = 0
        val stakeAmount = BigDecimal("10.00")

        //when
        val exception = assertThrows<BusinessRuntimeException> {
            gameService.playNumbersGame(invalidBetValue, stakeAmount)
        }

        //then
        assertEquals(ErrorCode.INVALID_BET_VALUE, exception.errorCode)
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.httpStatus)
    }

    @Test
    fun playNumbersGame_should_return_bet_dto_when_bet_is_valid_and_wins() {
        //given
        val betValue = 5
        val stakeAmount = BigDecimal("10.00")

        whenever(randomNumberHelper.randomNumber(1, 11)).thenReturn(betValue)

        //when
        val result = gameService.playNumbersGame(betValue, stakeAmount)

        //then
        assertEquals(stakeAmount * BigDecimal.TEN, result.winAmount)
        assertEquals(betValue, result.betValue)
        assertEquals(betValue, result.actualValue)

        verify(randomNumberHelper).randomNumber(1, 11)
    }

    @Test
    fun playNumbersGame_should_return_bet_dto_when_bet_is_one_off_and_wins() {
        //given
        val betValue = 5
        val stakeAmount = BigDecimal("10.00")
        val actualValue = 6  // 1 off from betValue
        whenever(randomNumberHelper.randomNumber(1, 11)).thenReturn(actualValue)

        //when
        val result = gameService.playNumbersGame(betValue, stakeAmount)

        //then
        assertEquals(stakeAmount * BigDecimal("5.00"), result.winAmount)
        assertEquals(betValue, result.betValue)
        assertEquals(actualValue, result.actualValue)

        verify(randomNumberHelper).randomNumber(1, 11)
    }

    @Test
    fun playNumbersGame_should_return_bet_dto_when_bet_is_two_off_and_wins() {
        //given
        val betValue = 5
        val stakeAmount = BigDecimal("10.00")
        val actualValue = 7  // 2 off from betValue
        whenever(randomNumberHelper.randomNumber(1, 11)).thenReturn(actualValue)

        //when

        val result = gameService.playNumbersGame(betValue, stakeAmount)

        //then
        assertEquals(stakeAmount * BigDecimal("0.50"), result.winAmount)
        assertEquals(betValue, result.betValue)
        assertEquals(actualValue, result.actualValue)

        verify(randomNumberHelper).randomNumber(1, 11)
    }

    @Test
    fun playNumbersGame_should_return_bet_dto_when_bet_does_not_win() {
        //given
        val betValue = 5
        val stakeAmount = BigDecimal("10.00")
        val actualValue = 8  // 3 off from betValue
        whenever(randomNumberHelper.randomNumber(1, 11)).thenReturn(actualValue)

        //when
        val result = gameService.playNumbersGame(betValue, stakeAmount)

        //then
        assertEquals(BigDecimal.ZERO, result.winAmount)
        assertEquals(betValue, result.betValue)
        assertEquals(actualValue, result.actualValue)

        verify(randomNumberHelper).randomNumber(1, 11)
    }
}
