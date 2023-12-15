package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import java.util.UUID

data class AccountGetRes(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val phoneNumber: String?,
    val email: String?,
    val userName: String?,
    val password: String
)