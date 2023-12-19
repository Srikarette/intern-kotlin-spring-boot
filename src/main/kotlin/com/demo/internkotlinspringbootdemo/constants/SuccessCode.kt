package com.demo.internkotlinspringbootdemo.constants

enum class SuccessCode (private val errorCode: Int, private val errorMessage: String) {
    GET_ALL_ACCOUNT_SUCCESS(1000,"GET ALL ACCOUNT SUCCESS"),
    GET_ACCOUNT_SUCCESS(1000,"GET ACCOUNT SUCCESS"),
    CREATE_ACCOUNT_SUCCESS(1000,"CREATE ACCOUNT SUCCESS"),
    UPDATE_ACCOUNT_SUCCESS(1000, "UPDATE ACCOUNT SUCCESS"),
    DELETE_ACCOUNT_SUCCESS(1000,"DELETE ACCOUNT SUCCESS"),

    //FOR BOOK SERVICE PART
    GET_ALL_PET_SUCCESS(1000,"GET ALL PET SUCCESS"),
    GET_PET_SUCCESS(1000,"GET PET SUCCESS"),
    CREATE_PET_SUCCESS(1000,"CREATE PET SUCCESS"),
    UPDATE_PET_SUCCESS(1000, "UPDATE PET SUCCESS"),
    DELETE_PET_SUCCESS(1000,"DELETE PET SUCCESS");


    fun getCode(): Int {
        return errorCode
    }

    fun getMessage(): String {
        return errorMessage
    }
}