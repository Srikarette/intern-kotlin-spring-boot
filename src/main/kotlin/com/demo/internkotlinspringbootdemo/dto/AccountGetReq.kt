package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class AccountGetReq(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String,
    val gender: GenderConstants,
    val phoneNumber: String?,
    val email: String?,
    val userName: String?,
    val password: String
)