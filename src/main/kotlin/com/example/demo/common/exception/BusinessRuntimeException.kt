package com.example.demo.common.exception

import com.example.demo.common.enum.ErrorCode

class BusinessRuntimeException(val errorCode: ErrorCode, val httpStatus: Int) : RuntimeException()