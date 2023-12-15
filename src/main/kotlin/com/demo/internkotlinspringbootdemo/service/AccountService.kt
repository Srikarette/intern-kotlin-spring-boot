package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.constants.BusinessException
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_ALREADY_EXISTS
import com.demo.internkotlinspringbootdemo.constants.ErrorCode.ACCOUNT_NOT_FOUND
import com.demo.internkotlinspringbootdemo.entity.Account
import com.demo.internkotlinspringbootdemo.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AccountService(private val accountRepository: AccountRepository) {
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
        val accountExistsByEmail = accountRepository.existsByEmail(account.email!!)

        if (accountExistsByEmail) {
            throw BusinessException(ACCOUNT_ALREADY_EXISTS.getCode(), ACCOUNT_ALREADY_EXISTS.getMessage())
        }

        // Hash the password
        val passwordEncoder = BCryptPasswordEncoder()
        val hashedPassword = passwordEncoder.encode(account.password)

        val accountToSave = account.copy(password = hashedPassword)
        return accountRepository.save(accountToSave)
    }
}