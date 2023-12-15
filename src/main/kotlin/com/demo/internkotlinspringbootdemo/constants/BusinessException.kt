package com.demo.internkotlinspringbootdemo.constants

data class BusinessException(val errorCode: Int, val errorMessage: String) : RuntimeException()