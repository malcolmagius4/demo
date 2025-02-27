package com.example.demo.common.enum

enum class ErrorCode(val errorCode: String, val errorMessage: String) {

    //1XXXX (Player based exception)
    USERNAME_TAKEN(errorCode = "10001", errorMessage = "Username Already In Use"),
    PLAYER_UUID_NOT_FOUND(errorCode = "10002", errorMessage = "Player Not Found By playerUuid"),

    //2XXXX (Game Based Exceptions)
    INVALID_BET_VALUE(errorCode = "20001", errorMessage = "Bet value must be between 1 and 10 (inclusive)"),
    INSUFFICIENT_WALLET_BALANCE(errorCode = "20002", errorMessage = "Not enough funds in wallet to complete this bet"),
}