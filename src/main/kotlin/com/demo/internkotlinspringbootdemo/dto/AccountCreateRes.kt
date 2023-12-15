package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AccountCreateRes(
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val userName: String?,
    val password: String
)
