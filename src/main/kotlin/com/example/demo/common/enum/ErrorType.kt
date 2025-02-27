package com.example.demo.common.enum

enum class ErrorType(val errorCode: String, val errorMessage: String) {

    USERNAME_TAKEN(errorCode = "10001", errorMessage = "Username Already In Use"),

    PLAYER_UUID_NOT_FOUND(errorCode = "10002", errorMessage = "Player Not Found By playerUuid"),
}