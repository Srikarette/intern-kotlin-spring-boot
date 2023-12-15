package com.demo.internkotlinspringbootdemo.dto

import java.util.UUID

data class PetDeleteReq(
    val ownerId: UUID,
)