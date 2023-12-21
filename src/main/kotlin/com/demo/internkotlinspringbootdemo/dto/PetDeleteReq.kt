package com.demo.internkotlinspringbootdemo.dto

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class PetDeleteReq(
    @field:NotBlank(message = "1: must enter user")
    val ownerId: UUID,
)