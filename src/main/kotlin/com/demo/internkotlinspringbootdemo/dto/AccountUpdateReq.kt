package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class AccountUpdateReq(
    @field:Size(max = 20, message = "first name must not longer than 20 letter")
    val firstName: String,

    @field:Size(max = 20, message = "last name must not longer than 20 letter")
    val lastName: String,

    val gender: GenderConstants,
    val phoneNumber: String?,
    val email: String?,

    @field:NotNull(message = "user name must not be null")
    val userName: String?,

    @field:NotNull(message = "user name must not be null")
    val password: String
)