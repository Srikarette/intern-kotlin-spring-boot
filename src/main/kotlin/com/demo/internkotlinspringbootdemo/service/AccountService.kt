package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_ALREADY_EXISTS
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PASSWORD_MISMATCH
import com.demo.internkotlinspringbootdemo.dto.AccountDeleteRes
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.mapper.AccountDeleteMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountUpdateMapper
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(private val accountRepository: AccountRepository) {
    val passwordEncoder = BCryptPasswordEncoder()
    fun getAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun getAccountById(id: UUID): Account {
        val existingAccount = accountRepository.findById(id)
        if (existingAccount.isEmpty) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        return existingAccount.get()
    }

    fun createAccount(account: Account): Account {
        val accountId = UUID.randomUUID()
        val accountExistsByEmail = accountRepository.existsByEmail(account.email!!)

        if (accountExistsByEmail) {
            throw BusinessException(ACCOUNT_ALREADY_EXISTS.getCode(), ACCOUNT_ALREADY_EXISTS.getMessage())
        }

        // Hash the password
        val passwordEncoder = BCryptPasswordEncoder()
        val hashedPassword = passwordEncoder.encode(account.password)

        val accountToSave = account.copy(id = accountId, password = hashedPassword)
        return accountRepository.save(accountToSave)
    }

    fun updateAccount(id: UUID, updatedAccount: AccountUpdateReq): AccountUpdateRes {
        val existingAccount = accountRepository.findById(id)

        if (existingAccount.isEmpty) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        val passwordChecker = passwordEncoder.matches(updatedAccount.password, existingAccount.get().password)

        if (!passwordChecker) {
            // Handle case where the old password is incorrect
            throw BusinessException(PASSWORD_MISMATCH.getCode(), PASSWORD_MISMATCH.getMessage())
        }

        val updatedEntity = existingAccount.get().copy(
            firstName = updatedAccount.firstName,
            lastName = updatedAccount.lastName,
            gender = updatedAccount.gender,
            phoneNumber = updatedAccount.phoneNumber,
            userName = updatedAccount.userName,
            password = updatedAccount.password,
            email = updatedAccount.email
        )
        val updatedAccountDetail = accountRepository.save(updatedEntity)
        return AccountUpdateMapper.toAccountUpdateRes(updatedAccountDetail)
    }

    fun deleteAccount(id: UUID): AccountDeleteRes {
        val existingAccount = accountRepository.findById(id)
        if (existingAccount.isEmpty) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        val deletedAccount = existingAccount.get()
        accountRepository.deleteById(id)

        return AccountDeleteMapper.toAccountDeleteRes(deletedAccount)
    }
}