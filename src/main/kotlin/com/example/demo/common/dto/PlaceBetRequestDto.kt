package com.example.demo.common.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.*

data class PlaceBetRequestDto(
    @field:Positive val stakeAmount: BigDecimal,
    @field:NotNull val betValue: Int,
    @field:NotNull val playerUuid: UUID
)
