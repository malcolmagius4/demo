package com.example.demo.common.dto

import java.math.BigDecimal

data class LeaderboardPlayerDto(
    val username: String,
    val name: String,
    val surname: String,
    val totalWinnings: BigDecimal
)
