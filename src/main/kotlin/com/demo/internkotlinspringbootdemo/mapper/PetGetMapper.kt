package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.PetGetRes
import com.demo.internkotlinspringbootdemo.entity.Pet

class PetGetMapper private constructor(){
    companion object{
        fun toPetGetRes(pet : Pet):PetGetRes{
            return PetGetRes(
                id = pet.id,
                ownerId = pet.ownerId,
                name = pet.name,
                gender = pet.gender,
                type = pet.type,
            )
        }
    }
}