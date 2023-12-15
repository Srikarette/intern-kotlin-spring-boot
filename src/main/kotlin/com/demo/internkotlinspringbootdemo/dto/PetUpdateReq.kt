package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class PetUpdateReq(
    val ownerId: UUID,
    val name: String,
    val gender: String?,
    val type: String?
)