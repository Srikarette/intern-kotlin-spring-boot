package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Pet

class PetUpdateMapper private constructor() {
    companion object {
        fun toPetUpdate(pet: Pet): PetUpdateRes {
            return PetUpdateRes(
                id = pet.id,
                ownerId = pet.ownerId,
                name = pet.name,
                gender = pet.gender,
                type = pet.type,
            )
        }
    }
}