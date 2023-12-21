package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class AccountDeleteReq (
    val id: UUID,
    val userName: String,
    val password: String
)