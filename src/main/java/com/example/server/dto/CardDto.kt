package com.example.server.dto

import java.time.LocalDateTime

data class CardDto(
        val id : Long,
        val code : String,
        val balance : Long,
        val isActive : Boolean,
        val customerId : Long,
        val transactions : List<TransactionDto>?
)
