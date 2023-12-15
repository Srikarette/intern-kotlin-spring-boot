package com.demo.internkotlinspringbootdemo.repository

import com.demo.internkotlinspringbootdemo.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PetRepository : JpaRepository<Pet, UUID> {}