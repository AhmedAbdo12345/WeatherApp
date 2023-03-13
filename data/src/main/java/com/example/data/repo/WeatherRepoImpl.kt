package com.example.data.repo

import com.example.data.api.ApiService
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.domain.repo.WeatherRepo
import java.sql.Timestamp

class WeatherRepoImpl(private val apiService: ApiService):WeatherRepo {
    override suspend fun getWeatherFromApi(
        lat: Double,
        lon: Double,
        lang:String,
        apiKey: String
    ): WeatherResponse =apiService.getWeather(lat,lon,lang,apiKey)


}