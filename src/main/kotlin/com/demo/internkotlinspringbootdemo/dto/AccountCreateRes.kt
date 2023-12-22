package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import java.util.UUID

data class AccountCreateRes(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val userName: String?,
    val password: String
)
