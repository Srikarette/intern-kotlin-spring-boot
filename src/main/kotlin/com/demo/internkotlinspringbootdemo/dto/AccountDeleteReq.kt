package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants

data class AccountDeleteReq (
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val userName: String?,
    val password: String
)