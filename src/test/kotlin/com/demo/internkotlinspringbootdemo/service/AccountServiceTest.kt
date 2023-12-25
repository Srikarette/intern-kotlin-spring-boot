package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_ALREADY_EXISTS
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PASSWORD_MISMATCH
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.MALE
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.OTHERS
import com.demo.internkotlinspringbootdemo.dto.AccountCreateReq
import com.demo.internkotlinspringbootdemo.dto.AccountDeleteReq
import com.demo.internkotlinspringbootdemo.dto.AccountDeleteRes
import com.demo.internkotlinspringbootdemo.dto.AccountGetReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.repository.AccountProjection
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional
import java.util.UUID

class AccountServiceTest {

    private val accountRepository: AccountRepository = mockk()
    private val passwordEncoder: BCryptPasswordEncoder = mockk()
    private val accountService = AccountService(passwordEncoder, accountRepository)

    @Nested
    @DisplayName("DeleteAccount")
    inner class DeleteAccountTest() {
        @Test
        fun `Given valid account ID When deleteAccount Then account is deleted`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedResult = AccountDeleteRes(
                id = accountId,
                firstName = "Gilbert Grant",
                lastName = "Shawn Sims",
                gender = OTHERS,
                userName = "username"
            )
            val deleteData = Account(
                id = accountId,
                firstName = "Gilbert Grant",
                lastName = "Shawn Sims",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "username",
                password = "password"
            )
            val request = AccountDeleteReq(
                id = accountId,
                userName = "username",
                password = "password"

            )

            every { accountRepository.findById(request.id) } returns Optional.of(deleteData)
            every { accountRepository.deleteById(request.id) } just Runs

            // When
            val actualResult = accountService.deleteAccount(accountId)

            // Then
            assertEquals(expectedResult, actualResult)
            verify(exactly = 1) { accountRepository.findById(accountId) }
            verify(exactly = 1) { accountRepository.deleteById(accountId) }
        }

        @Test
        fun `Given invalid account ID When deleteAccount Then BusinessException is thrown`() {
            // Given
            val invalidAccountId = UUID.randomUUID()
            val expectedException = BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
            every { accountRepository.findById(invalidAccountId) } returns Optional.empty()

            // When-Then
            val exception = assertThrows(BusinessException::class.java) {
                accountService.deleteAccount(invalidAccountId)
            }

            assertEquals(expectedException, exception)

            verify(exactly = 1) { accountRepository.findById(invalidAccountId) }
            verify(exactly = 0) { accountRepository.deleteById(invalidAccountId) }
        }
    }

    @Nested
    @DisplayName("GetAccount")
    inner class GetAccountTest() {
        @Test
        fun `Given valid request When getAllAccounts Then all accounts are returned`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedAccount = listOf(
                Account(
                    id = accountId,
                    firstName = "Elton Norris",
                    lastName = "Ned Bailey",
                    gender = OTHERS,
                    phoneNumber = "0123456",
                    email = "test@example.com",
                    userName = "username",
                    password = "sollicitudin"
                ),
                Account(
                    id = accountId,
                    firstName = "Elton Norris",
                    lastName = "Ned Bailey",
                    gender = OTHERS,
                    phoneNumber = "0123456",
                    email = "test@example.com",
                    userName = "username",
                    password = "sollicitudin"
                )
            )
            every { accountRepository.findAll() } returns expectedAccount
            // When
            val actualResult = accountService.getAllAccounts()
            // Then
            assertEquals(expectedAccount, actualResult)
            verify(exactly = 1) { accountRepository.findAll() }
        }

        @Test
        fun `Given valid account ID When getAccountById Then account is returned`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedAccount = Account(
                id = accountId,
                firstName = "Harold Hendrix",
                lastName = "Jeffry Gomez",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "username",
                password = "password"
            )
            val accountData = Account(
                id = accountId,
                firstName = "Harold Hendrix",
                lastName = "Jeffry Gomez",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "username",
                password = "password"
            )

            val request = AccountGetReq(
                id = accountId,
                firstName = "Harold Hendrix",
                lastName = "Jeffry Gomez",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "username",
                password = "password"
            )
            every { accountRepository.findById(accountId) } returns Optional.of(accountData)

            // When
            val actualAccount = accountService.getAccountById(accountId)

            // Then
            assertEquals(expectedAccount, actualAccount)
            verify(exactly = 1) { accountRepository.findById(accountId) }
        }

        @Test
        fun `Given invalid account ID when getAccountById then BusinessException is thrown`() {
            // Given
            val invalidAccountId = UUID.randomUUID()
            val expectedException = BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
            every { accountRepository.findById(invalidAccountId) } returns Optional.empty()

            // When-Then
            val actualException = assertThrows(BusinessException::class.java) {
                accountService.getAccountById(invalidAccountId)
            }

            assertEquals(expectedException, actualException)

            // Verify that findById was called
            verify(exactly = 1) { accountRepository.findById(invalidAccountId) }
        }
    }

    @Nested
    @DisplayName("CreateAccount")
    inner class CreateAccountTest() {
        private val hashedPassword = "hkljkl"

        @Test
        fun `Give valid create request when createAccount then account is saved`() {
            // Given
            val expectedResult = Account(
                firstName = "Stefan Vinson",
                lastName = "Kathie Reid",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "Test@example.com",
                userName = "stefan1",
                password = hashedPassword
            )

            val accountData = Account(
                firstName = "Stefan Vinson",
                lastName = "Kathie Reid",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "Test@example.com",
                userName = "stefan1",
                password = hashedPassword
            )

            val request = AccountCreateReq(
                firstName = "Stefan Vinson",
                lastName = "Kathie Reid",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "Test@example.com",
                userName = "stefan1",
                password = "password"
            )

            every { accountRepository.existsByEmail(request.email!!) } returns false
            every { accountRepository.save(accountData) } returns accountData
            every { passwordEncoder.encode(request.password) } returns hashedPassword

            // When
            val actualResult = accountService.createAccount(request)

            // Then
            assertEquals(expectedResult, actualResult)

            verify(exactly = 1) { accountRepository.existsByEmail(expectedResult.email!!) }
            verify(exactly = 1) { accountRepository.save(accountData) }
            verify(exactly = 1) { passwordEncoder.encode(request.password) }
        }

        @Test
        fun `Given account with existing email when createAccount then BusinessException is thrown`() {
            // Given
            val accountCreateReq = AccountCreateReq(
                firstName = "Laverne Martin",
                lastName = "Steve Duke",
                gender = OTHERS,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "testUser",
                password = "password"
            )
            every { accountRepository.existsByEmail(any()) } returns true

            // When-Then
            val exception = assertThrows(BusinessException::class.java) {
                accountService.createAccount(accountCreateReq)
            }
            assertEquals(ACCOUNT_ALREADY_EXISTS.getCode(), exception.errorCode)
            assertEquals(ACCOUNT_ALREADY_EXISTS.getMessage(), exception.errorMessage)
        }
    }

    @Nested
    @DisplayName("UpdateAccount")
    inner class UpdateAccountTest() {
        private val hashedPassword = "hkljkl"

        @Test
        fun `Given valid update request when updateAccount then account is updated`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedResult = AccountUpdateRes(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val accountData = Account(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val request = AccountUpdateReq(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = "password"
            )
            every { accountRepository.findById(accountId) } returns Optional.of(accountData)
            every { passwordEncoder.matches(request.password, hashedPassword) } returns true
            every { accountRepository.save(accountData) } returns accountData
            // When
            val actualResult = accountService.updateAccount(accountId, request)

            // Then
            assertEquals(expectedResult, actualResult)

            verify(exactly = 1) { accountRepository.findById(accountId) }
            verify(exactly = 1) { passwordEncoder.matches(request.password, hashedPassword) }
            verify(exactly = 1) { accountRepository.save(accountData) }
        }

        @Test
        fun `Given invalid account ID when updateAccount then BusinessException is thrown`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedResult = AccountUpdateRes(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val accountData = Account(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val request = AccountUpdateReq(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = "password"
            )
            val expectedException = BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
            every { accountRepository.findById(accountId) } returns Optional.empty()

            // When-Then
            val exception = assertThrows(BusinessException::class.java) {
                accountService.updateAccount(accountId, request)
            }

            assertEquals(expectedException, exception)

            verify(exactly = 1) { accountRepository.findById(accountId) }
            verify(exactly = 0) { accountRepository.save(accountData) }
        }

        @Test
        fun `Given invalid password ID when updateAccount then BusinessException is thrown`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedResult = AccountUpdateRes(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val accountData = Account(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = hashedPassword
            )

            val request = AccountUpdateReq(
                firstName = "newFirstName",
                lastName = "newLastName",
                gender = MALE,
                phoneNumber = "0123456",
                email = "test@example.com",
                userName = "newUserName",
                password = "password"
            )
            val expectedException = BusinessException(PASSWORD_MISMATCH.getCode(), PASSWORD_MISMATCH.getMessage())

            every { accountRepository.findById(accountId) } returns Optional.of(accountData)
            every { passwordEncoder.matches(request.password, hashedPassword) } returns false
            every { accountRepository.save(accountData) } returns accountData

            // When-Then
            val exception = assertThrows(BusinessException::class.java) {
                accountService.updateAccount(accountId, request)
            }

            assertEquals(expectedException, exception)

            verify(exactly = 1) { accountRepository.findById(accountId) }
            verify(exactly = 0) { accountRepository.save(accountData) }
        }
    }

    @Nested
    @DisplayName("ProjectionAccount")
    inner class ProjectionAccountTest() {
        @Test
        fun `Given valid account ID When getAccountPetByAccountId Then AccountProjection is returned`() {
            // Given
            val accountId = UUID.randomUUID()
            val expectedProjection = createMockAccountProjection(accountId)
            every { accountRepository.existsById(accountId) } returns true
            every { accountRepository.getUserPetCountsById(accountId) } returns expectedProjection

            // When
            val actualProjection = accountService.getAccountPetByAccountId(accountId)

            // Then
            assertEquals(expectedProjection, actualProjection)
            verify(exactly = 1) { accountRepository.existsById(accountId) }
            verify(exactly = 1) { accountRepository.getUserPetCountsById(accountId) }

        }

        @Test
        fun `Given invalid account ID When getAccountPetByAccountId Then BusinessException is thrown`() {
            // Given
            val invalidAccountId = UUID.randomUUID()
            val expectException = BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
            every { accountRepository.existsById(invalidAccountId) } returns false

            // When-Then
            val exception = assertThrows(BusinessException::class.java) {
                accountService.getAccountPetByAccountId(invalidAccountId)
            }
            assertEquals(expectException, exception)
            // Verify that existsById was called
            verify(exactly = 1) { accountRepository.existsById(invalidAccountId) }
            // Verify that getUserPetCountsById was not called
            verify(exactly = 0) { accountRepository.getUserPetCountsById(any()) }
        }

        @Test
        fun `Given valid request When getAccountPetCount Then List of AccountProjection is returned`() {
            // Given
            val expectedProjections = createMockAccountProjections()
            every { accountRepository.getUserPetCounts() } returns expectedProjections

            // When
            val actualProjections = accountService.getAccountPetCount()

            // Then
            assertEquals(expectedProjections, actualProjections)
            verify(exactly = 1) { accountRepository.getUserPetCounts() }
        }
    }

    private fun createMockAccountProjections(): List<AccountProjection> {
        val userId1 = UUID.randomUUID()
        val userId2 = UUID.randomUUID()

        return listOf(

        )
    }

    private fun createMockAccountProjection(userId: UUID, fullName: String, petCount: Long): AccountProjection {
        return object : AccountProjection {
            override val userId: UUID = userId
            override val fullName: String = fullName
            override val petCount: Long = petCount
        }
    }

    private fun createMockAccountProjection(userId: UUID): AccountProjection {
        return object : AccountProjection {
            override val userId: UUID = userId
            override val fullName: String = "John Doe"
            override val petCount: Long = 2
        }
    }
}
