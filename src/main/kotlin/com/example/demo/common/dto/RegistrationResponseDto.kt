package com.example.demo.common.dto

import java.util.UUID

data class RegistrationResponseDto(
    val playerUuid: UUID,
    val name: String,
    val surname: String,
    val username: String,
    val walletBalance: Int
)