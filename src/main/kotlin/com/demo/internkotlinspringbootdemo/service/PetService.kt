package com.demo.internkotlinspringbootdemo.service

import com.demo.internkotlinspringbootdemo.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(private val petRepository: PetRepository) {
}