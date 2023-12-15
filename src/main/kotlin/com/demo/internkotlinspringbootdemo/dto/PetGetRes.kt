package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class PetGetRes(
    val id: UUID? = null,
    val ownerId: UUID,
    val name: String,
    val gender: String?,
    val type: String?
)