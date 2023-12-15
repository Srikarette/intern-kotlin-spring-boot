package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.entity.Pet

class PetDeleteMapper private constructor(){
    companion object{
        fun toPetDeleteRes(pet: Pet):PetDeleteRes{
            return PetDeleteRes(
                id = pet.id,
                ownerId = pet.ownerId,
                name = pet.name,
                gender = pet.gender,
                type = pet.type,
            )
        }
    }
}