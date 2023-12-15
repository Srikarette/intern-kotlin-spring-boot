package com.demo.internkotlinspringbootdemo.repository

import com.demo.internkotlinspringbootdemo.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<Account, UUID> {}