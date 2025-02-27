package com.example.demo.common.exception

import com.example.demo.common.enum.ErrorType

class BusinessRuntimeException(val errorType: ErrorType, val httpStatus: Int) : RuntimeException()