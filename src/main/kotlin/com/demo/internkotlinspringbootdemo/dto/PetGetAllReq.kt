package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class PetGetAllReq(
    val id: UUID? = null,
    val ownerId: UUID? = null,
    val name: String? = null,
    val gender: String? = null,
    val type: String? = null
)
