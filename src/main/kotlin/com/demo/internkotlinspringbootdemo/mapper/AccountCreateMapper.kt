package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountCreateRes
import com.demo.internkotlinspringbootdemo.entity.Account

class AccountCreateMapper private constructor() {
    companion object {
        fun toAccountCreateRes(account: Account): AccountCreateRes {
            return AccountCreateRes(
                firstName = account.firstName,
                lastName = account.lastName,
                gender = account.gender,
                userName = account.userName,
                password = account.password
            )
        }
    }
}