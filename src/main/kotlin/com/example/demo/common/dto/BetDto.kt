package com.example.demo.common.dto

import java.time.Instant

data class BetDto(
    val stakeAmount: Int,
    val winAmount: Int,
    val betValue: Int,
    val actualValue: Int,
    val timestamp: Instant
)
