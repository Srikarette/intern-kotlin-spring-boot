package com.demo.internkotlinspringbootdemo.controller

import com.demo.internkotlinspringbootdemo.constants.ErrorCode.VALIDATION_ERROR
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.MALE
import com.demo.internkotlinspringbootdemo.constants.PetTypes.CAT
import com.demo.internkotlinspringbootdemo.constants.PetTypes.DOG
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.CREATE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.DELETE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_ALL_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.UPDATE_PET_SUCCESS
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetGetReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateRes
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import com.demo.internkotlinspringbootdemo.entity.Pet
import com.demo.internkotlinspringbootdemo.service.PetService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class PetControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {
    @MockkBean
    private lateinit var petService: PetService

    companion object {
        @JvmStatic
        fun provideInvalidCreatePet(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "ownerId is null",
                    PetCreateReq(
                        ownerId = null,
                        name = "example pet",
                        gender = MALE.name,
                        type = CAT
                    ),
                    listOf(
                        "ownerId must not be Null",
                    )
                ),
                Arguments.of(
                    "name is null",
                    PetCreateReq(
                        ownerId = UUID.randomUUID(),
                        name = null,
                        gender = MALE.name,
                        type = CAT
                    ),
                    listOf(
                        "name must not be Null",
                    )
                ),
                Arguments.of(
                    "type is null",
                    PetCreateReq(
                        ownerId = UUID.randomUUID(),
                        name = "phuriphon",
                        gender = MALE.name,
                        type = null
                    ),
                    listOf(
                        "type must not be Null"
                    )
                ),
                Arguments.of(
                    "everything is null",
                    PetCreateReq(
                        ownerId = null,
                        name = null,
                        gender = MALE.name,
                        type = null
                    ),
                    listOf(
                        "ownerId must not be Null",
                        "name must not be Null",
                        "type must not be Null",
                    )
                )
            )
        }

        //
        @JvmStatic
        fun provideInvalidGetPet(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "petId is null",
                    PetGetReq(
                        id = null,
                        ownerId = UUID.randomUUID(),
                        name = "phuriphon",
                        gender = "FEMALE",
                        type = DOG
                    ),
                    listOf(
                        "petId must not be null"
                    )
                ),
                Arguments.of(
                    "ownerId is null",
                    PetGetReq(
                        id = UUID.randomUUID(),
                        ownerId = null,
                        name = "phuriphon",
                        gender = "FEMALE",
                        type = DOG
                    ),
                    listOf(
                        "ownerId must not be null"
                    )
                ),
                Arguments.of(
                    "everything is null",
                    PetGetReq(
                        id = null,
                        ownerId = null,
                        name = "phuriphon",
                        gender = "FEMALE",
                        type = DOG
                    ),
                    listOf(
                        "petId must not be null",
                        "ownerId must not be null",
                    )
                )
            )
        }
        //

        @JvmStatic
        fun provideInvalidUpdateReq(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "petId is null",
                    PetUpdateReq(
                        id = null,
                        ownerId = UUID.randomUUID(),
                        name = "phuriphon"
                    ),
                    listOf(
                        "petId must not be null"
                    )
                ),
                Arguments.of(
                    "ownerId is null",
                    PetUpdateReq(
                        id = UUID.randomUUID(),
                        ownerId = null,
                        name = "phuriphon"
                    ),
                    listOf(
                        "ownerId must not be null"
                    )
                ),
                Arguments.of(
                    "name is null",
                    PetUpdateReq(
                        id = UUID.randomUUID(),
                        ownerId = UUID.randomUUID(),
                        name = null
                    ),
                    listOf(
                        "name must not be null"
                    )
                ),
                Arguments.of(
                    "everything is null",
                    PetUpdateReq(
                        id = null,
                        ownerId = null,
                        name = null,
                    ),
                    listOf(
                        "ownerId must not be null",
                        "petId must not be null",
                        "name must not be null"
                    )
                )

            )
        }

        //
        @JvmStatic
        fun provideInvalidDeleteReq(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "petId is Null",
                    PetDeleteReq(
                        id = null,
                        ownerId = UUID.randomUUID(),
                    ),
                    listOf(
                        "petId must not be Null",
                    )
                ),
                Arguments.of(
                    "ownerId is Null",
                    PetDeleteReq(
                        id = UUID.randomUUID(),
                        ownerId = null,
                    ),
                    listOf(
                        "ownerId must not be Null"
                    )
                ),
                Arguments.of(
                    "everything is Null",
                    PetDeleteReq(
                        id = null,
                        ownerId = null,
                    ),
                    listOf(
                        "petId must not be Null",
                        "ownerId must not be Null",
                    )
                )
            )
        }
    }

    @Nested
    @DisplayName("CreatePet")
    inner class CreatePetTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.demo.internkotlinspringbootdemo.controller.PetControllerTest#provideInvalidCreatePet")
        fun `Given invalid request When createPet Then validation error`(
            testCase: String,
            req: PetCreateReq,
            errorResponseList: List<String>
        ) {
            //Given
            val expectedException =
                TemplateResponse(VALIDATION_ERROR.getCode(), VALIDATION_ERROR.getMessage(), errorResponseList)

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `Given valid When createPet Then success`() {
            val ownerId = UUID.randomUUID()
            val petId = UUID.randomUUID()
            //Given
            val request = PetCreateReq(
                ownerId = ownerId,
                name = "James Shannon",
                gender = "FEMALE",
                type = DOG
            )
            val expectedPet = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG

            )
            val petDetail = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )

            val expectException =
                TemplateResponse(CREATE_PET_SUCCESS.getCode(), CREATE_PET_SUCCESS.getMessage(), expectedPet)

            every { petService.createPet(request) } returns petDetail

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectException))
                )
        }
    }

    @Nested
    @DisplayName("GetPet")
    inner class GetPetTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.demo.internkotlinspringbootdemo.controller.PetControllerTest#provideInvalidGetPet")
        fun `Given invalid request When createPet Then validation error`(
            testCase: String,
            req: PetGetReq,
            errorResponseList: List<String>
        ) {
            //Given
            val expectedException =
                TemplateResponse(VALIDATION_ERROR.getCode(), VALIDATION_ERROR.getMessage(), errorResponseList)

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/get")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `Given validRequest When getPet Then success`() {
            val ownerId = UUID.randomUUID()
            val petId = UUID.randomUUID()
            //Given
            val expectedPet = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val petDetail = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val request = PetGetReq(
                id = petId,
                ownerId = ownerId,
                name = null,
                gender = null,
                type = CAT
            )

            val expectException =
                TemplateResponse(GET_PET_SUCCESS.getCode(), GET_PET_SUCCESS.getMessage(), expectedPet)

            every { petService.getPetById(request) } returns petDetail

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/get")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )

            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectException))
                )
        }

        @Test
        fun `Given validRequest When getAllPet Then success`() {
            val ownerId = UUID.randomUUID()
            val petId = UUID.randomUUID()
            //Given
            val expectedPet = listOf(
                Pet(
                    id = petId, ownerId = ownerId, name = "Ada Cote", gender = null, type = DOG
                ),
                Pet(
                    id = petId, ownerId = ownerId, name = "Ada Cote", gender = null, type = DOG
                )
            )

            val petDetail = listOf(
                Pet(
                    id = petId, ownerId = ownerId, name = "Ada Cote", gender = null, type = DOG
                ),
                Pet(
                    id = petId, ownerId = ownerId, name = "Ada Cote", gender = null, type = DOG
                )
            )

            val expectException =
                TemplateResponse(GET_ALL_PET_SUCCESS.getCode(), GET_ALL_PET_SUCCESS.getMessage(), expectedPet)

            every { petService.getAllPets() } returns expectedPet

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet")
                    .contentType(MediaType.APPLICATION_JSON)
            )

            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectException))
                )
        }
    }

    @Nested
    @DisplayName("DeletePet")
    inner class DeletePetTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.demo.internkotlinspringbootdemo.controller.PetControllerTest#provideInvalidDeleteReq")
        fun `Give Invalid request When deletePet Then throw exception`(
            testCase: String,
            req: PetDeleteReq,
            errorResponseList: List<String>
        ) {
            //Given
            val expectedException =
                TemplateResponse(VALIDATION_ERROR.getCode(), VALIDATION_ERROR.getMessage(), errorResponseList)
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }

        @Test
        fun `Give valid request When deleteRet Then throw success`(){
            val ownerId = UUID.randomUUID()
            val petId = UUID.randomUUID()
            //Given
            val expectedPet = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val petDetail = PetDeleteRes(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val request = PetDeleteReq(
                id = petId,
                ownerId = ownerId,
            )

            val expectException =
                TemplateResponse(DELETE_PET_SUCCESS.getCode(), DELETE_PET_SUCCESS.getMessage(), expectedPet)

            every { petService.deletePet(request) }  returns petDetail

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectException))
                )
        }
    }

    @Nested
    @DisplayName("UpdatePet")
    inner class UpdatePetTest(){
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.demo.internkotlinspringbootdemo.controller.PetControllerTest#provideInvalidUpdateReq")
        fun `Give invalid When updatePet Then throw exception`(
            testCase: String,
            req: PetUpdateReq,
            errorResponseList: List<String>
        ){
            //Give
            val expectedException =
                TemplateResponse(VALIDATION_ERROR.getCode(),VALIDATION_ERROR.getMessage(),errorResponseList)
            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedException))
                )
        }
        @Test
        fun `Give valid request When updatePet Then throw success`(){
            val ownerId = UUID.randomUUID()
            val petId = UUID.randomUUID()
            //Given
            val expectedPet = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val petDetail = PetUpdateRes(
                id = petId,
                ownerId = ownerId,
                name = "Art Prince",
                gender = "FEMALE",
                type = DOG
            )
            val request = PetUpdateReq(
                id = petId,
                ownerId = ownerId,
                name = "newName"
            )

            val expectException =
                TemplateResponse(UPDATE_PET_SUCCESS.getCode(), UPDATE_PET_SUCCESS.getMessage(), expectedPet)

            every { petService.updatePet(request) }  returns petDetail

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/pet/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            // Then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(
                    MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectException))
                )
        }
    }
}