package com.example.server.dto

import com.example.server.model.TransactionType
import java.time.LocalDateTime

data class TransactionDto(
        val id : Long,
        val type : TransactionType,
        val creationDate : LocalDateTime,
        val cardId : Long
)
