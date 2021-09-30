package com.example.server.dto

data class UpdateCardBalanceRequest(
        val balance : Long,
        val code : String
)