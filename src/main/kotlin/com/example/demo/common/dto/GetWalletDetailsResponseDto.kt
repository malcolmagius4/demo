package com.example.demo.common.dto

import java.math.BigDecimal

data class GetWalletDetailsResponseDto(
    val walletBalance: BigDecimal,
    val transactions: List<TransactionDto>
)
