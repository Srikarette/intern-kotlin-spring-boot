package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import java.util.UUID

data class PetUpdateRes(
    val id: UUID? = null,
    val ownerId: UUID,
    val name: String,
    val gender: String?,
    val type: PetTypes?
)