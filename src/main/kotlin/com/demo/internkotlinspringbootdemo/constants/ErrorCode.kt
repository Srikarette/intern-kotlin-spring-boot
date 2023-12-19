package com.demo.internkotlinspringbootdemo.constants

enum class ErrorCode(private val errorCode: Int, private val errorMessage: String) {
    ACCOUNT_ALREADY_EXISTS(2000, "Account already exists"),
    ACCOUNT_NOT_FOUND(2100,"Account not found"),
    PASSWORD_MISMATCH(2200, "FAIL TO UPDATE PASSWORD MISMATCH"),
    VALIDATION_ERROR(5000,"Validation error"),

    //////////PET PART
    PET_NOT_FOUND(2100,"Pet not found");

    fun getCode(): Int {
        return errorCode
    }

    fun getMessage(): String {
        return errorMessage
    }

}