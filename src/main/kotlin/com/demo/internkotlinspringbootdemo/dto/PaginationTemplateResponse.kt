package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.entity.PageData

data class PaginationTemplateResponse<Any>(
    val code: Int,
    val message: String,
    val pageData: PageData,
    val data: Any? = null,

)