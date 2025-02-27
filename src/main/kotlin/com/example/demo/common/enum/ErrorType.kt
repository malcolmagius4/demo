package com.example.demo.common.enum

enum class ErrorType(val errorCode: String, val errorMessage: String) {

    USERNAME_TAKEN(errorCode = "10001", errorMessage = "Username Already In Use"),
}