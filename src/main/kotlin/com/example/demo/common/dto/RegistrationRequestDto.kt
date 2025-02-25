package com.example.demo.common.dto

import jakarta.validation.constraints.NotEmpty

data class RegistrationRequestDto(
    @NotEmpty val name: String,
    @NotEmpty val surname: String,
    @NotEmpty val username: String
)