package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.constants.GenderConstants
import com.demo.internkotlinspringbootdemo.dto.AccountGetAllRes
import com.demo.internkotlinspringbootdemo.entity.Account

class AccountGetAllMapper private constructor() {
    companion object {
        fun toAccountGetAllRes(accounts: List<Account>): List<AccountGetAllRes> {
            return accounts.map { toAccountData(it) }
        }

        private fun toAccountData(account: Account): AccountGetAllRes {
            return AccountGetAllRes(
                id = account.id,
                userName = account.userName,
                firstName = account.firstName,
                lastName = account.lastName,
                password = account.password,
                gender = account.gender,
                phoneNumber = account.phoneNumber,
                email = account.email,
            )
        }
    }
}