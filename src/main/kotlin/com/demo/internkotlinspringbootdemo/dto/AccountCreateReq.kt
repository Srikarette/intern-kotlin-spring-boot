package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AccountCreateReq(
    @field:Size(max = 20, message = "1:first name must not longer than 20 letter")
    val firstName: String? = null,

    @field:Size(max = 20, message = "2:last name must not longer than 20 letter")
    val lastName: String? = null,

    val gender: GenderConstants,
    val phoneNumber: String? = null,
    val email: String? = null,

    @field:NotNull(message = "3:username must not be null")
    @field:NotBlank(message = "4:username must not be blank")
    val userName: String? = null,

    @field:NotNull(message = "5:password must not be null")
    @field:NotBlank(message = "6:password must not be blank")
    val password: String? = null
)
