package com.example.domain.entity.ModelApi

import com.example.domain.entity.ModelApi.Current
import com.example.domain.entity.ModelApi.Daily
import com.example.domain.entity.ModelApi.Hourly
import com.example.domain.entity.ModelApi.Minutely

data class WeatherResponse(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)