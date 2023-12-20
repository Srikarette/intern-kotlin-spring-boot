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
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    @Column(name = "gender")
    @Enumerated(STRING)
    var gender: GenderConstants,

    @Column(name = "phone_number")
    var phoneNumber: String?,

    @Column(name = "email")
    var email: String?,

    @Column(name = "username")
    var userName: String?,

    @Column(name = "password")
    var password: String
)