package com.demo.internkotlinspringbootdemo.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetDeleteReq(
    @field:NotNull(message = "1:ownerId must not be Null")
    val ownerId: UUID? = null,
    @field:NotNull(message = "2:petId must not be Null")
    val id: UUID? = null,
)