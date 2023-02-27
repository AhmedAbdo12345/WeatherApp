package com.example.domain.usecase

import com.example.domain.repo.WeatherRepo
import java.sql.Timestamp

class GetWeatherUseCase(private val weatherRepo: WeatherRepo) {
    suspend operator fun invoke(lat:Double,  lon:Double,apiKey:String)=weatherRepo.getWeatherFromApi(lat,lon, apiKey)
}