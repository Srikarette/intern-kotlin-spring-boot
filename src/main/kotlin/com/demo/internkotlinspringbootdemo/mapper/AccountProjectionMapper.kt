package com.demo.internkotlinspringbootdemo.mapper

import com.demo.internkotlinspringbootdemo.dto.AccountProjectionRes
import com.demo.internkotlinspringbootdemo.repository.AccountProjection

class AccountProjectionMapper private constructor(){
    companion object{
        fun toAccountProjectionRes(accountProjection: List<AccountProjection>):List<AccountProjectionRes>{
            return accountProjection.map { toProjectionData(it) }
        }

        private fun toProjectionData(accountProjection: AccountProjection):AccountProjectionRes{
            return AccountProjectionRes(
                userId = accountProjection.userId,
                fullName = accountProjection.fullName,
                petCount = accountProjection.petCount
            )
        }
    }
}