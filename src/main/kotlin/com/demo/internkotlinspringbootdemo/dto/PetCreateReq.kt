package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class PetCreateReq(
    @field:NotNull(message = "1:ownerId must not be Null")
    val ownerId: UUID? = null,

    @field:NotNull(message = "2:name must not be Null")
    val name: String?,

    val gender: String?,

    @field:NotNull(message = "3:type must not be Null")
    val type: PetTypes?
)