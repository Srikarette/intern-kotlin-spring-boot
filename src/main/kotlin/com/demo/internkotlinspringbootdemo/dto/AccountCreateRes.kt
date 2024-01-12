package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import java.util.UUID

data class AccountCreateRes(
    val id: UUID? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var gender: GenderConstants,
    var phoneNumber: String? = null,
    var email: String? = null,
    var userName: String,
    var password: String,
){
}
