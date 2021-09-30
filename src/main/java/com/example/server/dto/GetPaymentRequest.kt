package com.example.server.dto

data class GetPaymentRequest(
        val code : String,
        val amount : Int
)
