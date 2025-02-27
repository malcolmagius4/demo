package com.example.demo.common.exception

import com.example.demo.common.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessRuntimeException::class)
    fun handleException(exception: BusinessRuntimeException) : ResponseEntity<ErrorDto> {

        return ResponseEntity<ErrorDto>(ErrorDto(errorCode = exception.errorType.errorCode, errorMessage = exception.errorType.errorMessage),
            HttpStatus.valueOf(exception.httpStatus))
    }

}