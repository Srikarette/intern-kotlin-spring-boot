package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetGetReq(
    @field:NotNull(message = "1:petId must not be null")
    val id: UUID? = null,
    
    @field:NotNull(message = "2:ownerId must not be null")
    val ownerId: UUID? = null,

    val name: String? = null,
    val gender: String? = null,
    val type: PetTypes? = null
) {
}