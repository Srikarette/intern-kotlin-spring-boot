package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class AccountProjectionRes(
    val userId: UUID,
    val fullName: String,
    val petCount: Long
)
