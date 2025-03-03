package com.example.demo.common.dto

import java.math.BigDecimal

data class BetDto(
    val stakeAmount: BigDecimal,
    val winAmount: BigDecimal,
    val betValue: Int,
    val actualValue: Int,
)
