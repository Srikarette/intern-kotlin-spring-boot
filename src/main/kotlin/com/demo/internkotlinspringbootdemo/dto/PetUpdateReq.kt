package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetUpdateReq(
    @field:NotNull(message = "1:ownerId must not be null")
    val ownerId: UUID? = null,

    @field:NotNull(message = "2:petId must not be null")
    val id: UUID? = null,

    @field:NotNull(message = "3:name must not be null")
    val name: String? = null,

    val gender: String? = null,

    val type: PetTypes? = null
)