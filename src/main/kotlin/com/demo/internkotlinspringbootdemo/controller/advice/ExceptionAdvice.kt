package com.demo.internkotlinspringbootdemo.controller.advice

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.VALIDATION_ERROR
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(e: BindException): TemplateResponse<Any> {
        return TemplateResponse(
            VALIDATION_ERROR.getCode(),
            VALIDATION_ERROR.getMessage(),
            e.fieldErrors
                .mapNotNull { it.defaultMessage?.split(":") }
                .filter { it.size == 2 }
                .sortedBy { it[0] }
                .map { it[1] }
                .toList()
        )
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCustomException(e: BusinessException): TemplateResponse<Any> {
        return TemplateResponse(
            e.errorCode,
            e.errorMessage
        )
    }
}