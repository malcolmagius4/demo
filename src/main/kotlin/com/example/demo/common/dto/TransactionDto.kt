package com.example.demo.common.dto

import com.example.demo.common.enum.TransactionType

data class TransactionDto(
    val amount: Int,
    val type: TransactionType,
)
