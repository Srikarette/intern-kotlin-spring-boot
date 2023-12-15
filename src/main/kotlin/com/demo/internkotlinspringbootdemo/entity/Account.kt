package com.demo.internkotlinspringbootdemo.entity

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @Column(name = "id")
    val id: UUID? = null,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @Column(name = "gender")
    @Enumerated(STRING)
    val gender: GenderConstants,

    @Column(name = "phone_number")
    val phoneNumber: String?,

    @Column(name = "email")
    val email: String?,

    @Column(name = "username")
    val userName: String?,

    @Column(name = "password")
    val password: String
)