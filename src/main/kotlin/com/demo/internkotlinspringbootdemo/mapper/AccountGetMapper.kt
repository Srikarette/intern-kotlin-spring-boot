package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountGetRes
import com.demo.internkotlinspringbootdemo.entity.Account

class AccountGetMapper private constructor(){
    companion object{
        fun toAccountGetRes(account: Account): AccountGetRes{
            return AccountGetRes(
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