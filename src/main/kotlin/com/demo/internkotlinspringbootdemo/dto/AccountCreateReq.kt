package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AccountCreateReq(
    @field:Size(max = 20, message = "1:first name must not longer than 20 letter")
    val firstName: String,

    @field:Size(max = 20, message = "2:last name must not longer than 20 letter")
    val lastName: String,

    val gender: GenderConstants,
    val phoneNumber: String?,
    val email: String?,

    @field:NotBlank(message = "3:username must not be null")
    val userName: String?,

    @field:NotBlank(message = "4:password must not be null")
    val password: String
)
