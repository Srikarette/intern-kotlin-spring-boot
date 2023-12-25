package com.demo.internkotlinspringbootdemo.controller

import com.demo.internkotlinspringbootdemo.constants.ErrorCode
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.MALE
import com.demo.internkotlinspringbootdemo.constants.PetTypes
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.CREATE_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.dto.AccountCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteReq
import com.demo.internkotlinspringbootdemo.dto.PetGetReq
import com.demo.internkotlinspringbootdemo.dto.PetUpdateReq
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import com.demo.internkotlinspringbootdemo.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class AccountControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) {
    private val accountRepository: AccountRepository = mockk()
    private val passwordEncoder: BCryptPasswordEncoder = mockk()

    @MockkBean
    private lateinit var accountService: AccountService

    companion object {
        @JvmStatic
        fun provideInvalidCreateAccount(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "first name longer than 20 letter",
                    AccountCreateReq(
                        firstName = "qwertyuiop[]asdfghjkl",
                        lastName = "Srika",
                        gender = MALE,
                        phoneNumber = "0123456789",
                        email = "test@example.com",
                        userName = "username",
                        password = "password"
                    ),
                    listOf(
                        "first name must not longer than 20 letter",
                    )
                ),
                Arguments.of(
                    "last name longer than 20 letter",
                    AccountCreateReq(
                        firstName = "Phuriphon",
                        lastName = "qwertyuiop[]asdfghjkl",
                        gender = MALE,
                        phoneNumber = "0123456789",
                        email = "test@example.com",
                        userName = "username",
                        password = "password"
                    ),
                    listOf(
                        "last name must not longer than 20 letter",
                    )
                ),
                Arguments.of(
                    "username is null",
                    AccountCreateReq(
                        firstName = "Phuriphon",
                        lastName = "qwertyuiop[",
                        gender = MALE,
                        phoneNumber = "0123456789",
                        email = "test@example.com",
                        userName = null,
                        password = "password"
                    ),
                    listOf(
                        "username must not be null",
                    )
                ),
                Arguments.of(
                    "password is null",
                    AccountCreateReq(
                        firstName = "Phuriphon",
                        lastName = "qwertyuiop[",
                        gender = MALE,
                        phoneNumber = "0123456789",
                        email = "test@example.com",
                        userName = "username",
                        password = null
                    ),
                    listOf(
                        "password must not be null",
                    )
                )
            )
        }

        //
        @JvmStatic
        fun provideInvalidGetAccount(): List<Arguments> {
            return listOf(
                Arguments.of(
                    "petId is null",
                    PetGetReq(
                        id = null,
                        ownerId = UUID.randomUUID(),
                        name = "phuriphon",
                        gender = "FEMALE",
                        type = PetTypes.DOG
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
                        type = PetTypes.DOG
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
                        type = PetTypes.DOG
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
                        "petId must not be Null"
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
    @DisplayName("CreateAccount")
    inner class CreateAccountTest {
        @ParameterizedTest(name = "GIVEN {0} WHEN createPet THEN {2}")
        @MethodSource("com.demo.internkotlinspringbootdemo.controller.AccountControllerTest#provideInvalidCreateAccount")
        fun `Given invalid request When createAccount Then validation error`(
            testCase: String,
            req: AccountCreateReq,
            errorResponseList: List<String>
        ) {
            //Given
            val expectedException =
                TemplateResponse(
                    ErrorCode.VALIDATION_ERROR.getCode(),
                    ErrorCode.VALIDATION_ERROR.getMessage(),
                    errorResponseList
                )

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/account/create")
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
        fun `Given valid When createAccount Then success`() {
            //Given
            val accountId = UUID.randomUUID()
            val request = AccountCreateReq(
                firstName = "kuerwieou",
                lastName = "kuerwie",
                gender = MALE,
                phoneNumber = "0123456789",
                email = "test@example.com",
                userName = "userName",
                password = "password"
            )
            val expectedAccount = Account(
                id = accountId,
                firstName = "kuerwieou",
                lastName = "kuerwie",
                gender = MALE,
                phoneNumber = "0123456789",
                email = "test@example.com",
                userName = "userName",
                password = "password"
            )
            val accountDetail = Account(
                id = accountId,
                firstName = "kuerwieou",
                lastName = "kuerwie",
                gender = MALE,
                phoneNumber = "0123456789",
                email = "test@example.com",
                userName = "userName",
                password = "password"
            )
            val expectException =
                TemplateResponse(
                    CREATE_ACCOUNT_SUCCESS.getCode(),
                    CREATE_ACCOUNT_SUCCESS.getMessage(),
                    expectedAccount
                )

            every { accountService.createAccount(request) } returns accountDetail

            // When
            val resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/account/create")
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