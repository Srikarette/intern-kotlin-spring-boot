
import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_ALREADY_EXISTS
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.MALE
import com.demo.internkotlinspringbootdemo.constants.GenderConstants.OTHERS
import com.demo.internkotlinspringbootdemo.dto.AccountCreateReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateReq
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.mapper.AccountDeleteMapper
import com.demo.internkotlinspringbootdemo.repository.AccountProjection
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import com.demo.internkotlinspringbootdemo.service.AccountService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional
import java.util.UUID

class AccountServiceTest {

    private val accountRepository: AccountRepository = mockk()
    private val accountService = AccountService(accountRepository)

    @Test
    fun `Given valid request when getAllAccounts then all accounts are returned`() {
        // Given
        val mockAccounts = listOf(
            getAllMockAccount("John", "Doe"),
            getAllMockAccount("Jane", "Smith"),
            getAllMockAccount("Bob", "Johnson")
        )
        every { accountRepository.findAll() } returns mockAccounts

        // When
        val allAccounts = accountService.getAllAccounts()

        // Then
        verify(exactly = 1) { accountRepository.findAll() }
        assertEquals(mockAccounts.size, allAccounts.size)
        assertEquals(mockAccounts, allAccounts)
    }

    @Test
    fun `Given valid account ID when getAccountById then account is returned`() {
        // Given
        val accountId = UUID.randomUUID()
        val expectedAccount = getByIdMockAccount("John", "Doe", accountId)
        every { accountRepository.findById(accountId) } returns Optional.of(expectedAccount)

        // When
        val actualAccount = accountService.getAccountById(accountId)

        // Then
        verify(exactly = 1) { accountRepository.findById(accountId) }
        assertEquals(expectedAccount, actualAccount)
    }

    @Test
    fun `Given invalid account ID when getAccountById then BusinessException is thrown`() {
        // Given
        val invalidAccountId = UUID.randomUUID()
        every { accountRepository.findById(invalidAccountId) } returns Optional.empty()

        // When-Then
        val exception = assertThrows(BusinessException::class.java) {
            accountService.getAccountById(invalidAccountId)
        }

        assertEquals(ACCOUNT_NOT_FOUND.getCode(), exception.errorCode)
        assertEquals(ACCOUNT_NOT_FOUND.getMessage(), exception.errorMessage)

        // Verify that findById was called
        verify(exactly = 1) { accountRepository.findById(invalidAccountId) }
    }

    @Test
    fun `Give valid create request when createAccount then account is saved`() {
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
        every { accountRepository.existsByEmail(any()) } returns false
        every { accountRepository.save(any()) } returns createMockAccount(accountCreateReq)

        // When
        val createdAccount = accountService.createAccount(accountCreateReq)

        // Then
        verify(exactly = 1) { accountRepository.existsByEmail(accountCreateReq.email!!) }
        verify(exactly = 1) { accountRepository.save(any()) }
        assertEquals(accountCreateReq.firstName, createdAccount.firstName)
        assertEquals(accountCreateReq.lastName, createdAccount.lastName)
        assertEquals(accountCreateReq.email, createdAccount.email)
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

    @Test
    fun `Given valid update request when updateAccount then account is updated`() {
        // Given
        val accountId = UUID.randomUUID()
        val accountUpdateReq = AccountUpdateReq(
            firstName = "Laverne Martin",
            lastName = "Steve Duke",
            gender = MALE,
            phoneNumber = "0123456",
            email = "test@example.com",
            userName = "testUser",
            password = "password"
        )
        val existingAccount = updateMockAccount(accountUpdateReq)
        every { accountRepository.findById(accountId) } returns Optional.of(existingAccount)
        every { accountRepository.save(any()) } returns existingAccount.copy(
            firstName = accountUpdateReq.firstName!!,
            lastName = accountUpdateReq.lastName!!,
            gender = accountUpdateReq.gender,
            phoneNumber = accountUpdateReq.phoneNumber,
            userName = accountUpdateReq.userName,
            password = accountUpdateReq.password,
            email = accountUpdateReq.email
        )

        // When
        val updatedAccount = accountService.updateAccount(accountId, accountUpdateReq)

        // Then
        verify(exactly = 1) { accountRepository.findById(accountId) }
        verify(exactly = 1) { accountRepository.save(any()) }
        assertEquals(accountUpdateReq.firstName, updatedAccount.firstName)
        assertEquals(accountUpdateReq.lastName, updatedAccount.lastName)
        assertEquals(accountUpdateReq.email, updatedAccount.email)
    }


    @Test
    fun `Given valid account ID when deleteAccount then account is deleted`() {
        // Given
        val accountId = UUID.randomUUID()
        val existingAccount = deleteMockAccount("John", "Doe", accountId)
        every { accountRepository.findById(accountId) } returns Optional.of(existingAccount)
        every { accountRepository.deleteById(accountId) } answers { /* do nothing */ }

        // When
        val deletedAccountRes = accountService.deleteAccount(accountId)

        // Then
        verify(exactly = 1) { accountRepository.findById(accountId) }
        verify(exactly = 1) { accountRepository.deleteById(accountId) }
        assertEquals(AccountDeleteMapper.toAccountDeleteRes(existingAccount), deletedAccountRes)
    }

    @Test
    fun `Given invalid account ID when deleteAccount then BusinessException is thrown`() {
        // Given
        val invalidAccountId = UUID.randomUUID()
        every { accountRepository.existsById(invalidAccountId) } returns false

        // When-Then
        val exception = assertThrows(BusinessException::class.java) {
            accountService.deleteAccount(invalidAccountId)
        }

        assertEquals(ACCOUNT_NOT_FOUND.getCode(), exception.errorCode)
        assertEquals(ACCOUNT_NOT_FOUND.getMessage(), exception.errorMessage)

        verify(exactly = 1) { accountRepository.existsById(invalidAccountId) }
        verify(exactly = 1) { accountRepository.deleteById(invalidAccountId) }
    }

    @Test
    fun `Given valid account ID when getAccountPetByAccountId then AccountProjection is returned`() {
        // Given
        val accountId = UUID.randomUUID()
        val expectedProjection = createMockAccountProjection(accountId)
        every { accountRepository.existsById(accountId) } returns true
        every { accountRepository.getUserPetCountsById(accountId) } returns expectedProjection

        // When
        val actualProjection = accountService.getAccountPetByAccountId(accountId)

        // Then
        verify(exactly = 1) { accountRepository.existsById(accountId) }
        verify(exactly = 1) { accountRepository.getUserPetCountsById(accountId) }
        assertEquals(expectedProjection, actualProjection)
    }

    @Test
    fun `Given invalid account ID when getAccountPetByAccountId then BusinessException is thrown`() {
        // Given
        val invalidAccountId = UUID.randomUUID()
        every { accountRepository.existsById(invalidAccountId) } returns false

        // When-Then
        val exception = assertThrows(BusinessException::class.java) {
            accountService.getAccountPetByAccountId(invalidAccountId)
        }

        assertEquals(ACCOUNT_NOT_FOUND.getCode(), exception.errorCode)
        assertEquals(ACCOUNT_NOT_FOUND.getMessage(), exception.errorMessage)

        // Verify that existsById was called
        verify(exactly = 1) { accountRepository.existsById(invalidAccountId) }
        // Verify that getUserPetCountsById was not called
        verify(exactly = 0) { accountRepository.getUserPetCountsById(any()) }
    }

    @Test
    fun `Given valid request when getAccountPetCount then List of AccountProjection is returned`() {
        // Given
        val expectedProjections = createMockAccountProjections()
        every { accountRepository.getUserPetCounts() } returns expectedProjections

        // When
        val actualProjections = accountService.getAccountPetCount()

        // Then
        verify(exactly = 1) { accountRepository.getUserPetCounts() }
        assertEquals(expectedProjections, actualProjections)
    }
    private fun updateMockAccount(account: AccountUpdateReq): Account {
        return Account(
            id = UUID.randomUUID(),
            firstName = account.firstName!!,
            lastName = account.lastName!!,
            gender = account.gender,
            phoneNumber = account.phoneNumber,
            email = account.email,
            userName = account.userName,
            password = BCryptPasswordEncoder().encode(account.password)
        )
    }

    private fun createMockAccount(account: AccountCreateReq): Account {
        return Account(
            id = UUID.randomUUID(),
            firstName = account.firstName,
            lastName = account.lastName,
            gender = account.gender,
            phoneNumber = account.phoneNumber,
            email = account.email,
            userName = account.userName,
            password = BCryptPasswordEncoder().encode(account.password)
        )
    }

    private fun getByIdMockAccount(firstName: String, lastName: String, id: UUID): Account {
        return Account(
            id = id,
            firstName = firstName,
            lastName = lastName,
            gender = MALE,
            phoneNumber = "1234567890",
            email = "test@example.com",
            userName = "testUser",
            password = "password"
        )
    }


    private fun getAllMockAccount(firstName: String, lastName: String): Account {
        return Account(
            id = UUID.randomUUID(),
            firstName = firstName,
            lastName = lastName,
            gender = MALE,
            phoneNumber = "1234567890",
            email = "test@example.com",
            userName = "testUser",
            password = "password"
        )
    }

    private fun deleteMockAccount(firstName: String, lastName: String, id: UUID): Account {
        return Account(
            id = id,
            firstName = firstName,
            lastName = lastName,
            gender = MALE,
            phoneNumber = "1234567890",
            email = "test@example.com",
            userName = "testUser",
            password = "password"
        )
    }

    private fun createMockAccountProjections(): List<AccountProjection> {
        val userId1 = UUID.randomUUID()
        val userId2 = UUID.randomUUID()

        return listOf(
            createMockAccountProjection(userId1, "John Doe", 2),
            createMockAccountProjection(userId2, "Jane Smith", 3)
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
