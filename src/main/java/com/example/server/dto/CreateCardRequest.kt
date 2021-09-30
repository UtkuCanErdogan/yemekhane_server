package com.example.server.dto

data class CreateCardRequest(
        val code : String,
        val customerId : Long
)
