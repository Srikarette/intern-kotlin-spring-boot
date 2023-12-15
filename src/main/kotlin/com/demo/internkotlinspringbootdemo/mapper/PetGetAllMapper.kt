package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.PetGetAllRes
import com.demo.internkotlinspringbootdemo.entity.Pet

class PetGetAllMapper private constructor() {
    companion object{
        fun toPetGetAllRes(pet : List<Pet>):List<PetGetAllRes>{
            return pet.map { toPetData(it) }
        }
        private fun toPetData(pet: Pet): PetGetAllRes {
            return PetGetAllRes(
                id = pet.id,
                ownerId = pet.ownerId,
                name = pet.name,
                gender = pet.gender,
                type = pet.type,
            )
        }
    }


}