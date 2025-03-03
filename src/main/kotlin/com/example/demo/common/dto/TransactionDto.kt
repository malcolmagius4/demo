package com.example.demo.common.dto

import com.example.demo.common.enum.TransactionType
import java.math.BigDecimal

data class TransactionDto(
    val amount: BigDecimal,
    val type: TransactionType,
)
