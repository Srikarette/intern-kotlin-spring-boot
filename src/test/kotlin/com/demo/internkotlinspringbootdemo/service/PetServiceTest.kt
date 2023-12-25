package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PET_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.FEMALE
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.MALE
import com.demo.internkotlinspringbootdemo.constants.PetTypes.CAT
import com.demo.internkotlinspringbootdemo.constants.PetTypes.DOG
import com.demo.internkotlinspringbootdemo.constants.PetTypes.OTHERS
import com.demo.internkotlinspringbootdemo.dto.PetCreateReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteReq
import com.demo.internkotlinspringbootdemo.dto.PetDeleteRes
import com.demo.internkotlinspringbootdemo.dto.PetGetReq
import com.demo.internkotlinspringbootdemo.entity.Pet
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import com.demo.internkotlinspringbootdemo.repository.PetRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.UUID

class PetServiceTest {
    private val accountRepository: AccountRepository = mockk()
    private val petRepository: PetRepository = mockk()
    private val petService = PetService(petRepository, accountRepository)

    @Nested
    @DisplayName("GetPet")
    inner class GetPetTest() {
        val petId = UUID.randomUUID()
        val ownerId = UUID.randomUUID()

        @Test
        fun `Given valid request When getAllPets Then all pets are returned`() {
            // Given
            val expectedPet = listOf(
                Pet(
                    id = petId,
                    ownerId = ownerId,
                    name = "Alyson Franks",
                    gender = MALE.name,
                    type = DOG
                ),
                Pet(
                    id = petId,
                    ownerId = ownerId,
                    name = "Alyson Franks",
                    gender = MALE.name,
                    type = DOG
                ),
            )
            every { petRepository.findAll() } returns expectedPet
            // When
            val actualResult = petService.getAllPets()
            // Then
            assertEquals(expectedPet, actualResult)
            verify(exactly = 1) { petRepository.findAll() }
        }

        @Test
        fun `Given valid pet ID When getPetById Then pet is returned`() {
            // Given
            val expectedPet = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Shanna Howell",
                gender = MALE.name,
                type = DOG
            )
            val petData = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Shanna Howell",
                gender = MALE.name,
                type = DOG
            )

            val request = PetGetReq(
                id = petId,
                ownerId = ownerId,
                name = "Bob",
                gender = FEMALE.name,
                type = CAT
            )
            every { petRepository.findById(petId) } returns Optional.of(petData)

            // When
            val actualPet = petService.getPetById(request)

            // Then
            assertEquals(expectedPet, actualPet)
            verify(exactly = 1) { petRepository.findById(petId) }
        }

        @Test
        fun `Given invalid pet ID when getPetById then BusinessException is thrown`() {
            // Given
            val invalidPetId = UUID.randomUUID()
            val request = PetGetReq(
                id = invalidPetId
            )
            val expectedException = BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
            every { petRepository.findById(invalidPetId) } returns Optional.empty()

            // When-Then
            val actualException = assertThrows(BusinessException::class.java) {
                petService.getPetById(request)
            }

            assertEquals(expectedException, actualException)

            // Verify that findById was called
            verify(exactly = 1) { petRepository.findById(invalidPetId) }
        }
    }

    @Nested
    @DisplayName("DeletePet")
    inner class DeletePetTest() {
        val petId = UUID.randomUUID()
        val ownerId = UUID.randomUUID()

        @Test
        fun `Given valid pet ID When deletePet Then pet is deleted`() {
            // Given
            val expectedResult = PetDeleteRes(
                id = petId,
                ownerId = ownerId,
                name = "Zachery Sanford",
                gender = MALE.name,
                type = OTHERS
            )
            val deleteData = Pet(
                id = petId,
                ownerId = ownerId,
                name = "Zachery Sanford",
                gender = MALE.name,
                type = OTHERS
            )
            val request = PetDeleteReq(
                id = petId,
                ownerId = ownerId,
            )
            every { accountRepository.existsById(ownerId) } returns true
            every { petRepository.findById(petId) } returns Optional.of(deleteData)
            every { petRepository.deleteById(petId) } just Runs

            // When
            val actualResult = petService.deletePet(request)

            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { petRepository.findById(petId) }
            verify(exactly = 1) { petRepository.deleteById(petId) }
        }

        @Test
        fun `Given invalid pet ID When deletePet Then BusinessException is thrown`() {
            // Given
            val invalidPetId = UUID.randomUUID()
            val request = PetDeleteReq(
                id = invalidPetId,
                ownerId = ownerId
            )
            val expectedException = BusinessException(PET_NOT_FOUND.getCode(), PET_NOT_FOUND.getMessage())
            every { petRepository.findById(invalidPetId) } returns Optional.empty()
            every { accountRepository.existsById(ownerId) } returns true

//             When-Then
            val exception = assertThrows(BusinessException::class.java) {
                petService.deletePet(request)
            }

            assertEquals(expectedException, exception)

            verify(exactly = 1) { petRepository.findById(invalidPetId) }
            verify(exactly = 0) { petRepository.deleteById(invalidPetId) }
        }

        @Test
        fun `Given invalid account ID When deletePet Then BusinessException is thrown`() {
            // Given
            val invalidPetId = UUID.randomUUID()
            val request = PetDeleteReq(
                id = invalidPetId,
                ownerId = ownerId
            )
            val expectedException = BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
            every { petRepository.findById(invalidPetId) } returns Optional.empty()
            every { accountRepository.existsById(ownerId) } returns false

//             When-Then
            val exception = assertThrows(BusinessException::class.java) {
                petService.deletePet(request)
            }

            assertEquals(expectedException, exception)

            verify(exactly = 0) { petRepository.findById(invalidPetId) }
            verify(exactly = 0) { petRepository.deleteById(invalidPetId) }
        }
    }

    @Nested
    @DisplayName("CreatePet")
    inner class CreatePetTest() {
        val petId = UUID.randomUUID()
        val ownerId = UUID.randomUUID()
        @Test
        fun `Give valid create request when createPet then pet is saved`() {
            // Given
            val expectedResult = Pet(
                id = petId,
                ownerId = ownerId,
                name = "German McMahon",
                gender = FEMALE.name,
                type = DOG
            )

            val petData = Pet(
                id = petId,
                ownerId = ownerId,
                name = "German McMahon",
                gender = FEMALE.name,
                type = DOG
            )

            val petDataDetail = Pet(
                ownerId = ownerId,
                name = "German McMahon",
                gender = FEMALE.name,
                type = DOG
            )

            val request = PetCreateReq(
                ownerId = ownerId,
                name = "German McMahon",
                gender = FEMALE.name,
                type = DOG
            )
            every { petRepository.save(petDataDetail) } returns petData
            every { accountRepository.existsById(ownerId) } returns true

            // When
            val actualResult = petService.createPet(request)

            // Then
            assertEquals(expectedResult, actualResult)

            verify(exactly = 1) { petRepository.save(petDataDetail) }
            verify (exactly = 1) { accountRepository.existsById(ownerId)}
        }
    }
}