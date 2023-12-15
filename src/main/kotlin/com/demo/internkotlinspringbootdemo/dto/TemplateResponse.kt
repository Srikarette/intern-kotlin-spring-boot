package com.demo.internkotlinspringbootdemo.dto

data class TemplateResponse<Any>(val code: Int, val message: String, val data: Any? = null)