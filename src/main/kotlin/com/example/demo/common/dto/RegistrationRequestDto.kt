package com.example.demo.common.dto

import jakarta.validation.constraints.NotEmpty

data class RegistrationRequestDto(
    @field:NotEmpty val name: String,
    @field:NotEmpty val surname: String,
    @field:NotEmpty val username: String
)