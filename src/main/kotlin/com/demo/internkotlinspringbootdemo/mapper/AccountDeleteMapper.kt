package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountDeleteRes
import com.demo.internkotlinspringbootdemo.entity.Account

class AccountDeleteMapper private constructor(){
    companion object{
        fun toAccountDeleteRes(account: Account):AccountDeleteRes{
            return AccountDeleteRes(
                id = account.id!!,
                firstName = account.firstName,
                lastName = account.lastName,
                gender = account.gender,
                userName = account.userName
            )
        }
    }
}