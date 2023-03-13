package com.example.weather.utils

import com.example.domain.entity.ModelApi.WeatherResponse


sealed class ApiStatus {
    data class Success(val weatherResponse: WeatherResponse): ApiStatus()
    data class Loading(val msg:String):ApiStatus()
    data class Failed(val msg:String):ApiStatus()

}