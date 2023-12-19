package com.demo.internkotlinspringbootdemo.repository

import java.util.UUID

interface AccountProjection {
    val userId: UUID
    val fullName: String
    val petCount: Long
}
