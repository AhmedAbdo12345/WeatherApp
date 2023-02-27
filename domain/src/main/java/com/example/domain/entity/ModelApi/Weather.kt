package com.example.domain.entity.ModelApi

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)