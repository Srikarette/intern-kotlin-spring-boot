package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PET_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.PetTypes.CAT
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Pet
import com.demo.internkotlinspringbootdemo.mapper.PetDeleteMapper
import com.demo.internkotlinspringbootdemo.mapper.PetUpdateMapper
import com.demo.internkotlinspringbootdemo.repository.PetRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PetService(private val petRepository: PetRepository) {
    fun getAllPets(): List<Pet> {
        return petRepository.findAll()
    }

    fun getPetById(id: UUID): Pet {
        val existingPet = petRepository.findById(id)
        if (existingPet.isEmpty) {
            throw BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
        }
        return existingPet.get()
    }

    fun createPet(pet: PetCreateReq): Pet {
        val petCreated = Pet(
            id = UUID.randomUUID(),
            ownerId = pet.ownerId,
            name = "Alexandria Hansen",
            gender = null,
            type = null
        )
        return petRepository.save(petCreated)
    }

    fun updateAccount(id: UUID, updatedPet: PetUpdateReq): PetUpdateRes {
        val existingPet = petRepository.findById(id)

        if (existingPet.isEmpty) {
            throw BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
        }

        val updatedEntity = existingPet.get().copy(
            ownerId = updatedPet.ownerId,
            name = updatedPet.name,
            gender = updatedPet.gender,
            type = CAT
        )
        val updatedPetDetail = petRepository.save(updatedEntity)
        return PetUpdateMapper.toPetUpdate(updatedPetDetail)
    }

    fun deleteAccount(id: UUID): PetDeleteRes {
        val existingPet = petRepository.findById(id)
        if (existingPet.isEmpty) {
            throw BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
        }
        val deletedPet = existingPet.get()
        petRepository.deleteById(id)

        return PetDeleteMapper.toPetDeleteRes(deletedPet)
    }
}