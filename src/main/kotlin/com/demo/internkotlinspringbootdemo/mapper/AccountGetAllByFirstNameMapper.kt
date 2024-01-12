package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountGetPageRes
import com.demo.internkotlinspringbootdemo.entity.Account
import org.springframework.data.domain.Page

class AccountGetAllByFirstNameMapper private constructor() {
    companion object {
        fun toAccountGetAllRes(accounts: Page<Account>): Page<AccountGetPageRes> {
            return accounts.map { toAccountData(it) }
        }
        private fun toAccountData(account: Account): AccountGetPageRes {
            return AccountGetPageRes(
                id = account.id,
                userName = account.userName,
                firstName = account.firstName!!,
                lastName = account.lastName!!,
                password = account.password,
                gender = account.gender,
                phoneNumber = account.phoneNumber,
                email = account.email,
            )
        }
    }}