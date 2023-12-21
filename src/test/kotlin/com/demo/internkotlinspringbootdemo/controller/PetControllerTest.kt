package com.demo.internkotlinspringbootdemo.controller

import com.demo.internkotlinspringbootdemo.constants.ErrorCode.VALIDATION_ERROR
import com.demo.internkotlinspringbootdemo.constants.PetTypes.CAT
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import com.demo.internkotlinspringbootdemo.service.PetService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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
                        gender = "MALE",
                        type = CAT
                    ),
                    listOf(
                        "ownerId must not be Null",
                    )
                )
            )
        }
    }

    @Nested
    @DisplayName("CreatePet")
    inner class CreatePetTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {1}")
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
                MockMvcRequestBuilders.post("/api/v1/pet/create")  // Specify the correct path
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
    }
}