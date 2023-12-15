package com.demo.internkotlinspringbootdemo.constants

enum class ErrorCode(private val errorCode: Int, private val errorMessage: String) {
    ACCOUNT_ALREADY_EXISTS(2000, "Account already exists"),
    ACCOUNT_NOT_FOUND(2100,"Account not found"),
    PASSWORD_MISMATCH(2200, "FAIL TO UPDATE PASSWORD MISMATCH"),
    AUTHOR_ALREADY_EXIST(2000,"A book with the same author or name already exists."),
    BOOK_NOT_FOUND(2100,"BOOK NOT FOUND"),
    VALIDATION_ERROR(5000,"Validation error");

    fun getCode(): Int {
        return errorCode
    }

    fun getMessage(): String {
        return errorMessage
    }

}