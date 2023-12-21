package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetUpdateReq(
    @field:NotNull(message = "1:ownerId must not be Null")
    val ownerId: UUID,

    @field:NotBlank(message = "2:name must have name")
    @field:NotNull(message = "3:name must not be Null")
    val name: String,

    val gender: String?,

    val type: PetTypes?
)