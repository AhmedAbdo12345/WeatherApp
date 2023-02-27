package com.example.domain.repo

import com.example.domain.entity.ModelApi.WeatherResponse
import java.sql.Timestamp

interface WeatherRepo {
   suspend fun getWeatherFromApi(lat:Double, lon:Double, apiKey:String): WeatherResponse
}