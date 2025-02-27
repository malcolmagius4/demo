package com.example.demo.common.dto

data class GetWalletDetailsResponseDto(
    val walletBalance: Int,
    val transactions: Set<TransactionDto>
)
