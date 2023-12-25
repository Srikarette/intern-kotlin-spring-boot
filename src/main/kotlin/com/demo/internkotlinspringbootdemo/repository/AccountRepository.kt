package com.demo.internkotlinspringbootdemo.repository

import com.demo.internkotlinspringbootdemo.entity.Account
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {
    @Query("""
     SELECT
        accounts.id AS userId,
        concat(accounts.first_name, ' ', accounts.last_name) as fullName,
        COUNT(pets.id) AS petCount
    FROM
        accounts
    LEFT JOIN
        pets ON accounts.id = pets.owner_id
    GROUP BY
    accounts.id
    """, nativeQuery = true)
    fun getUserPetCounts(): List<AccountProjection>


    @Query("""
     SELECT
        accounts.id AS userId,
        concat(accounts.first_name, ' ', accounts.last_name) as fullName,
        COUNT(pets.id) AS petCount
    FROM
        accounts
    LEFT JOIN
        pets ON accounts.id = pets.owner_id
    WHERE
        accounts.id = :ownerId
    GROUP BY
    accounts.id
    """, nativeQuery = true)
    fun getUserPetCountsById(ownerId: UUID): AccountProjection

    fun findAllByOrderByFirstNameAsc(pageable: Pageable): Page<Account>
    fun existsByEmail(name: String): Boolean

}