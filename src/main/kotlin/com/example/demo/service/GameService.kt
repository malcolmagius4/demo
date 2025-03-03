package com.example.demo.service

import com.example.demo.common.dto.BetDto
import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.util.RandomNumberHelper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.math.abs

@Service
class GameService(private val randomNumberHelper: RandomNumberHelper) {

    private val FIVE: BigDecimal = BigDecimal("5.00")
    private val ZERO_POINT_FIVE: BigDecimal = BigDecimal("0.50")

    fun playNumbersGame(betValue: Int, stakeAmount: BigDecimal): BetDto {
        if (betValue <= 1 || betValue > 10) {
            throw BusinessRuntimeException(ErrorCode.INVALID_BET_VALUE, HttpStatus.BAD_REQUEST.value())
        }

        val actualValue = randomNumberHelper.randomNumber(1, 11)

        val difference = abs(betValue - actualValue)

        val winAmount = when (difference) {
            0 -> stakeAmount * BigDecimal.TEN
            1 -> stakeAmount * FIVE
            2 -> stakeAmount * ZERO_POINT_FIVE
            else -> BigDecimal.ZERO
        }

        return BetDto(stakeAmount = stakeAmount, winAmount = winAmount, betValue = betValue, actualValue = actualValue)
    }
}