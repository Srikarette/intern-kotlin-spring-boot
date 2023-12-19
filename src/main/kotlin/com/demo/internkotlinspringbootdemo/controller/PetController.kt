package com.demo.internkotlinspringbootdemo.controller

import com.demo.internkotlinspringbootdemo.constants.SuccessCode.CREATE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.DELETE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_ALL_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.UPDATE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetCreateRes
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetGetAllRes
import com.demo.internkotlinspringbootdemo.dto.PetGetRes
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import com.demo.internkotlinspringbootdemo.mapper.PetCreateMapper
import com.demo.internkotlinspringbootdemo.mapper.PetGetAllMapper
import com.demo.internkotlinspringbootdemo.mapper.PetGetMapper
import com.demo.internkotlinspringbootdemo.service.PetService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/pet")
class PetController(private val petService: PetService) {
    @PostMapping
    fun getAllAccounts(): TemplateResponse<List<PetGetAllRes>> {
        val pets = petService.getAllPets()
        val petsDetails = PetGetAllMapper.toPetGetAllRes(pets)
        return TemplateResponse(
            GET_ALL_PET_SUCCESS.getCode(),
            GET_ALL_PET_SUCCESS.getMessage(),
            petsDetails
        )
    }

    @PostMapping("/{id}")
    fun getAccountById(@PathVariable id: UUID): TemplateResponse<PetGetRes> {
        val pet = petService.getPetById(id)
        val petDetails = PetGetMapper.toPetGetRes(pet)
        return TemplateResponse(
            GET_PET_SUCCESS.getCode(),
            GET_PET_SUCCESS.getMessage(),
            petDetails
        )
    }

    @PostMapping("/create")
    fun createPet(@Valid @RequestBody pet: PetCreateReq): TemplateResponse<PetCreateRes> {
        val petToCreate = petService.createPet(pet)
        val accountDetail = PetCreateMapper.toPetCreateRes(petToCreate)
        return TemplateResponse(
            CREATE_PET_SUCCESS.getCode(),
            CREATE_PET_SUCCESS.getMessage(),
            accountDetail
        )
    }

    @PostMapping("/update/{id}")
    fun updateAccount(
        @PathVariable id: UUID,
        @Valid @RequestBody updatedPet: PetUpdateReq
    ): TemplateResponse<PetUpdateRes> {
        val petToUpdate = petService.updateAccount(id, updatedPet)
        return TemplateResponse(
            UPDATE_PET_SUCCESS.getCode(),
            UPDATE_PET_SUCCESS.getMessage(),
            petToUpdate
        )
    }

    @PostMapping("/delete/{id}")
    fun deleteAccount(@PathVariable id: UUID): TemplateResponse<PetDeleteRes> {
        val petToDelete = petService.deleteAccount(id)
        return TemplateResponse(
            DELETE_PET_SUCCESS.getCode(),
            DELETE_PET_SUCCESS.getMessage(),
            petToDelete
        )
    }
}