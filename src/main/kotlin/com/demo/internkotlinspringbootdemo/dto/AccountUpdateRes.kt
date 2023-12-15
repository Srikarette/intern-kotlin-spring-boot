package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AccountUpdateRes (
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val phoneNumber: String?,
    val email: String?,
    val userName: String?,
    val password: String
)