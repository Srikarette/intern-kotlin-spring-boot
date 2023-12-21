package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetCreateReq(
    val ownerId: UUID,
    @field:NotBlank(message = "1: pet must have name")
    val name: String,

    val gender: String?,

    @field:NotNull(message = "2: pet must have type")
    val type: PetTypes
)