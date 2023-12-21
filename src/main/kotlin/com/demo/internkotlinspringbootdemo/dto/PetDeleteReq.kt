package com.demo.internkotlinspringbootdemo.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetDeleteReq(
    @field:NotNull(message = "1:ownerId must not be Null")
    val ownerId: UUID,
)