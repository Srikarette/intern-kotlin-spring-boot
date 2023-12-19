package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountProjectionRes
import com.demo.internkotlinspringbootdemo.repository.AccountProjection

class AccountProjectionGetByIdMapper private constructor(){
    companion object{
        fun toAccountGetPetById(accountProjection: AccountProjection): AccountProjectionRes{
            return AccountProjectionRes(
                userId = accountProjection.userId,
                fullName = accountProjection.fullName,
                petCount = accountProjection.petCount
            )
        }
    }
}