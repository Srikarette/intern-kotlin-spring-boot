package com.demo.internkotlinspringbootdemo.dto

import jakarta.persistence.Column
import jakarta.persistence.Id
import java.util.UUID

data class PetCreateReq(
    val ownerId: UUID,
    val name: String,
    val gender: String?,
    val type: String?
)