package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Pet
import com.demo.internkotlinspringbootdemo.mapper.PetDeleteMapper
import com.demo.internkotlinspringbootdemo.mapper.PetUpdateMapper
import com.demo.internkotlinspringbootdemo.repository.PetRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PetService(private val petRepository: PetRepository) {
    val passwordEncoder = BCryptPasswordEncoder()
    fun getAllPets(): List<Pet> {
        return petRepository.findAll()
    }

    fun getPetById(id: UUID): Pet {
        val existingPet = petRepository.findById(id)
        if (existingPet.isEmpty) {
            throw BusinessException(ErrorCode.ACCOUNT_NOT_FOUND.getCode(), ErrorCode.ACCOUNT_NOT_FOUND.getMessage())
        }
        return existingPet.get()
    }

    fun createPet(pet: Pet): Pet {
        val petId = UUID.randomUUID()

        val petToSave = pet.copy(id = petId)
        return petRepository.save(petToSave)
    }

    fun updateAccount(id: UUID, updatedPet: PetUpdateReq): PetUpdateRes {
        val existingPet = petRepository.findById(id)

        if (existingPet.isEmpty) {
            throw BusinessException(ErrorCode.ACCOUNT_NOT_FOUND.getCode(), ErrorCode.ACCOUNT_NOT_FOUND.getMessage())
        }

        val updatedEntity = existingPet.get().copy(
            ownerId = updatedPet.ownerId,
            name = updatedPet.name,
            gender = updatedPet.gender,
            type = updatedPet.type
        )
        val updatedPetDetail = petRepository.save(updatedEntity)
        return PetUpdateMapper.toPetUpdate(updatedPetDetail)
    }

    fun deleteAccount(id: UUID): PetDeleteRes {
        val existingPet = petRepository.findById(id)
        if (existingPet.isEmpty) {
            throw BusinessException(ErrorCode.ACCOUNT_NOT_FOUND.getCode(), ErrorCode.ACCOUNT_NOT_FOUND.getMessage())
        }
        val deletedPet = existingPet.get()
        petRepository.deleteById(id)

        return PetDeleteMapper.toPetDeleteRes(deletedPet)
    }
}