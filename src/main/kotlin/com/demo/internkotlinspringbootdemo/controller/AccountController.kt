package com.demo.internkotlinspringbootdemo.controller

import com.demo.internkotlinspringbootdemo.constants.SuccessCode.CREATE_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.DELETE_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.GET_ALL_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.constants.SuccessCode.UPDATE_ACCOUNT_SUCCESS
import com.demo.internkotlinspringbootdemo.dto.AccountCreateReq
import com.demo.internkotlinspringbootdemo.dto.AccountCreateRes
import com.demo.internkotlinspringbootdemo.dto.AccountDeleteRes
import com.demo.internkotlinspringbootdemo.dto.AccountGetAllRes
import com.demo.internkotlinspringbootdemo.dto.AccountGetPageReq
import com.demo.internkotlinspringbootdemo.dto.AccountGetRes
import com.demo.internkotlinspringbootdemo.dto.AccountProjectionRes
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateReq
import com.demo.internkotlinspringbootdemo.dto.AccountUpdateRes
import com.demo.internkotlinspringbootdemo.dto.PaginationTemplateResponse
import com.demo.internkotlinspringbootdemo.dto.TemplateResponse
import com.demo.internkotlinspringbootdemo.entity.Page
import com.demo.internkotlinspringbootdemo.mapper.AccountCreateMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountGetAllByFirstNameMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountGetAllMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountGetMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountProjectionGetByIdMapper
import com.demo.internkotlinspringbootdemo.mapper.AccountProjectionMapper
import com.demo.internkotlinspringbootdemo.service.AccountService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/account")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    fun getAllAccounts(): TemplateResponse<List<AccountGetAllRes>> {
        val accounts = accountService.getAllAccounts()
        val accountsDetails = AccountGetAllMapper.toAccountGetAllRes(accounts)
        return TemplateResponse(
            GET_ALL_ACCOUNT_SUCCESS.getCode(),
            GET_ALL_ACCOUNT_SUCCESS.getMessage(),
            accountsDetails
        )
    }

    @PostMapping("/accounts")
    fun getAllAccountsByFirstName(
        @Valid
        @RequestBody currentPage: AccountGetPageReq,
        @RequestParam(defaultValue = "5") pageSize: Int
    ): PaginationTemplateResponse {
        val accounts = accountService.getAllAccountsByFirstname(currentPage.currentPage, pageSize)
        val accountsDetails = AccountGetAllByFirstNameMapper.toAccountGetAllRes(accounts)
        val pageMetadata = Page(
            currentPage = accounts.number,
            totalPages = accounts.totalPages
        )
        return PaginationTemplateResponse(
            GET_ALL_ACCOUNT_SUCCESS.getCode(),
            GET_ALL_ACCOUNT_SUCCESS.getMessage(),
            pageMetadata,
            accountsDetails
        )
    }


    @PostMapping("/count")
    fun getAccountPetCount(): TemplateResponse<List<AccountProjectionRes>> {
        val account = accountService.getAccountPetCount()
        val accountDetail = AccountProjectionMapper.toAccountProjectionRes(account)
        return TemplateResponse(
            GET_ACCOUNT_SUCCESS.getCode(),
            GET_ACCOUNT_SUCCESS.getMessage(),
            accountDetail
        )
    }

    @PostMapping("/count/{id}")
    fun getAccountPetCount(@PathVariable id: UUID): TemplateResponse<AccountProjectionRes> {
        val account = accountService.getAccountPetByAccountId(id)
        val accountDetail = AccountProjectionGetByIdMapper.toAccountGetPetById(account)
        return TemplateResponse(
            GET_ACCOUNT_SUCCESS.getCode(),
            GET_ACCOUNT_SUCCESS.getMessage(),
            accountDetail
        )
    }

    @PostMapping("/{id}")
    fun getAccountById(@PathVariable id: UUID): TemplateResponse<AccountGetRes> {
        val account = accountService.getAccountById(id)
        val accountDetails = AccountGetMapper.toAccountGetRes(account)
        return TemplateResponse(
            GET_ACCOUNT_SUCCESS.getCode(),
            GET_ACCOUNT_SUCCESS.getMessage(),
            accountDetails
        )
    }

    @PostMapping("/create")
    fun createAccount(@Valid @RequestBody account: AccountCreateReq): TemplateResponse<AccountCreateRes> {
        val accountToCreate = accountService.createAccount(account)
        val accountDetail = AccountCreateMapper.toAccountCreateRes(accountToCreate)
        return TemplateResponse(
            CREATE_ACCOUNT_SUCCESS.getCode(),
            CREATE_ACCOUNT_SUCCESS.getMessage(),
            accountDetail
        )
    }

    @PostMapping("/update/{id}")
    fun updateAccount(
        @PathVariable id: UUID,
        @Valid @RequestBody updatedAccount: AccountUpdateReq
    ): TemplateResponse<AccountUpdateRes> {
        val accountToUpdate = accountService.updateAccount(id, updatedAccount)
        return TemplateResponse(
            UPDATE_ACCOUNT_SUCCESS.getCode(),
            UPDATE_ACCOUNT_SUCCESS.getMessage(),
            accountToUpdate
        )
    }

    @PostMapping("/delete/{id}")
    fun deleteAccount(@PathVariable id: UUID): TemplateResponse<AccountDeleteRes> {
        val accountToDelete = accountService.deleteAccount(id)
        return TemplateResponse(
            DELETE_ACCOUNT_SUCCESS.getCode(),
            DELETE_ACCOUNT_SUCCESS.getMessage(),
            accountToDelete
        )
    }
}