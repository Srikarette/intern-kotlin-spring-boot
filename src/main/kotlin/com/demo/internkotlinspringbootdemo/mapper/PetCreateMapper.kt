package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.PetCreateRes
import com.demo.internkotlinspringbootdemo.entity.Pet

class PetCreateMapper private constructor(){
    companion object{
        fun toPetCreateRes(pet : Pet):PetCreateRes{
            return PetCreateRes(
                id = pet.id,
                ownerId = pet.ownerId,
                name = pet.name,
                gender = pet.gender,
                type = pet.type,
            )
        }
    }
}