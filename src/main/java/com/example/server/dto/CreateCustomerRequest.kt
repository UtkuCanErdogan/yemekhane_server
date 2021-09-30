package com.example.server.dto

data class CreateCustomerRequest(
        val tc : Long,
        val mail : String,
        val name : String,
        val surname : String,
        val age : Int,
)
