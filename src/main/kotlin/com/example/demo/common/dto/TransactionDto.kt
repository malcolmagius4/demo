package com.example.demo.common.dto

import com.example.demo.common.enum.TransactionType
import java.time.Instant

data class TransactionDto(
    val amount: Int,
    val type: TransactionType,
    val timestamp: Instant,
)
