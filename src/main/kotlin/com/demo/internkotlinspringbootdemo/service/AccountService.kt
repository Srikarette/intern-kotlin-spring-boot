package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_ALREADY_EXISTS
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.PASSWORD_MISMATCH
import com.demo.internkotlinspringbootdemo.dto.AccountCreateReq
import com.demo.internkotlinspringbootdemo.dto.AccountDeleteRes
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.mapper.AccountDeleteMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountUpdateMapper
import com.demo.internkotlinspringbootdemo.repository.AccountProjection
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(
    private val passwordEncoder: BCryptPasswordEncoder,
    private val accountRepository: AccountRepository
) {
    fun getAllAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun getAccountPetCount(): List<AccountProjection> {
        return accountRepository.getUserPetCounts()
    }

    fun getAccountPetByAccountId(id: UUID): AccountProjection {
        val existingAccount = accountRepository.existsById(id)
        if (!existingAccount) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        return accountRepository.getUserPetCountsById(id)
    }


    fun getAccountById(id: UUID): Account {
        val existingAccount = accountRepository.findById(id)
        if (existingAccount.isEmpty) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        return existingAccount.get()
    }

    fun createAccount(account: AccountCreateReq): Account {
        val accountExistsByEmail = accountRepository.existsByEmail(account.email!!)

        if (accountExistsByEmail) {
            throw BusinessException(ACCOUNT_ALREADY_EXISTS.getCode(), ACCOUNT_ALREADY_EXISTS.getMessage())
        }

        // Hash the password
        val hashedPassword = passwordEncoder.encode(account.password)

        val accountCreated = Account(
            firstName = account.firstName!!,
            lastName = account.lastName!!,
            gender = account.gender,
            phoneNumber = account.phoneNumber,
            email = account.email,
            userName = account.userName,
            password = hashedPassword,
        )
        return accountRepository.save(accountCreated)
    }

    fun updateAccount(id: UUID, updatedAccount: AccountUpdateReq): AccountUpdateRes {
         val existingAccount = accountRepository.findById(id)

        if (existingAccount.isEmpty) {
            throw BusinessException(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage())
        }
        val accountData = existingAccount.get()
        val passwordChecker = passwordEncoder.matches(updatedAccount.password, accountData.password)

        if (!passwordChecker) {
            // Handle case where the old password is incorrect
            throw BusinessException(PASSWORD_MISMATCH.getCode(), PASSWORD_MISMATCH.getMessage())
        }

        val updatedEntity = accountData
        updatedEntity.apply {
            firstName = updatedAccount.firstName?:firstName
            lastName = updatedAccount.lastName?:lastName
            gender = updatedAccount.gender?:gender
            phoneNumber = updatedAccount.phoneNumber?:phoneNumber
            email = updatedAccount.email?:email
            userName = updatedAccount.userName
            password = accountData.password
        }
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