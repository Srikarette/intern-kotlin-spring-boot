package com.demo.internkotlinspringbootdemo.constants

enum class SuccessCode (private val errorCode: Int, private val errorMessage: String) {
    GET_ALL_ACCOUNT_SUCCESS(1000,"GET ALL ACCOUNT SUCCESS"),
    GET_ACCOUNT_SUCCESS(1000,"GET ACCOUNT SUCCESS"),
    CREATE_ACCOUNT_SUCCESS(1000,"CREATE ACCOUNT SUCCESS"),
    UPDATE_ACCOUNT_SUCCESS(1000, "UPDATE ACCOUNT SUCCESS"),
    DELETE_ACCOUNT_SUCCESS(1000,"DELETE ACCOUNT SUCCESS"),

    //FOR BOOK SERVICE PART
    GET_ALL_PET_SUCCESS(1000,"GET ALL BOOK SUCCESS"),
    GET_PET_SUCCESS(1000,"GET BOOK SUCCESS"),
    CREATE_PET_SUCCESS(1000,"CREATE BOOK SUCCESS"),
    UPDATE_PET_SUCCESS(1000, "UPDATE BOOK SUCCESS"),
    DELETE_PET_SUCCESS(1000,"DELETE BOOK SUCCESS");


    fun getCode(): Int {
        return errorCode
    }

    fun getMessage(): String {
        return errorMessage
    }
}