package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PET_NOT_FOUND
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Pet
import com.demo.internkotlinspringbootdemo.mapper.PetDeleteMapper
import com.demo.internkotlinspringbootdemo.mapper.PetUpdateMapper
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import com.demo.internkotlinspringbootdemo.repository.PetRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PetService(private val petRepository: PetRepository, private val accountRepository: AccountRepository) {

    private fun validateOwnerIdExists(ownerId: UUID) {
        if (!accountRepository.existsById(ownerId)) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
    }

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
        validateOwnerIdExists(pet.ownerId)

        val petCreated = Pet(
            id = UUID.randomUUID(),
            ownerId = pet.ownerId,
            name = "Alexandria Hansen",
            gender = pet.gender,
            type = pet.type
        )
        return petRepository.save(petCreated)
    }

    fun updatePet(id: UUID, updatedPet: PetUpdateReq): PetUpdateRes {
        validateOwnerIdExists(updatedPet.ownerId)
        val existingPet = petRepository.findById(id)

        if (existingPet.isEmpty) {
            throw BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
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

    fun deletePet(id: UUID, deleteReq: PetDeleteReq): PetDeleteRes {
        validateOwnerIdExists(deleteReq.ownerId);
        val existingPet = petRepository.findById(id)
        if (existingPet.isEmpty) {
            throw BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
        }
        val deletedPet = existingPet.get()
        petRepository.deleteById(id)

        return PetDeleteMapper.toPetDeleteRes(deletedPet)
    }
}