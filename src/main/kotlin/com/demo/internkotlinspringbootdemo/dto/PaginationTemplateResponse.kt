package com.demo.internkotlinspringbootdemo.dto

import com.demo.internkotlinspringbootdemo.entity.Page

data class PaginationTemplateResponse(
    val code: Int,
    val message: String,
    val pagedata: Page,
    val data: Any? = null,
)