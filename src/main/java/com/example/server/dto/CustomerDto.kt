package com.example.server.dto

data class CustomerDto(
        val id : Long,
        val tc : Long,
        val mail : String,
        val name : String,
        val surname : String,
        val age : Int,
        val isActive : Boolean,
        val card : CardDto?
)
