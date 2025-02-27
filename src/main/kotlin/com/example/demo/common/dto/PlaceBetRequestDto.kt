package com.example.demo.common.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class PlaceBetRequestDto(
    @field:Positive val stakeAmount: Int,
    @field:NotNull val betValue: Int,
    @field:NotNull val playerUuid: UUID
)
