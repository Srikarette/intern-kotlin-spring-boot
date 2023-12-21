package com.demo.internkotlinspringbootdemo.entity

import com.demo.internkotlinspringbootdemo.constants.PetTypes
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "pets")
data class Pet(
    @Id
    @Column(name = "id")
    val id: UUID? = null,

    val ownerId: UUID,

    @Column(name = "name")
    val name: String,

    @Column(name = "gender")
    val gender: String?,

    @Column(name = "type")
    val type: PetTypes?
)