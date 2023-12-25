package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountUpdateRes
import com.demo.internkotlinspringbootdemo.entity.Account

class AccountUpdateMapper private constructor() {
    companion object {
        fun toAccountUpdateRes(account: Account): AccountUpdateRes {
            return AccountUpdateRes(
                firstName = account.firstName!!,
                lastName = account.lastName!!,
                gender = account.gender,
                phoneNumber = account.phoneNumber,
                userName = account.userName,
                password = account.password,
                email = account.email
            )
        }
    }
}