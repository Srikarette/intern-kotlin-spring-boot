package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AccountUpdateReq(
    @field:Size(max = 20, message = "1:first name must not longer than 20 letter")
    var firstName: String?,

    @field:Size(max = 20, message = "2:last name must not longer than 20 letter")
    var lastName: String?,

    var gender: GenderConstants?,
    var phoneNumber: String?,
    var email: String?,

    @field:NotNull(message = "3:user name must not be null")
    var userName: String,

    @field:NotNull(message = "4:user name must not be null")
    var password: String
)