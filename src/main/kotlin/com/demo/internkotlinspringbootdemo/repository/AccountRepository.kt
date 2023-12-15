package com.demo.internkotlinspringbootdemo.repository

import com.demo.internkotlinspringbootdemo.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
    fun existsByEmail(name: String): Boolean
}