package com.demo.internkotlinspringbootdemo.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetGetReq(
    @field:NotNull(message = "1:pet id must not be null")
    val id: UUID? = null,
    
    @field:NotNull(message = "2:owner id must not be null")
    val ownerId: UUID,

    val name: String,
    val gender: String?,
    val type: String?
) {
}