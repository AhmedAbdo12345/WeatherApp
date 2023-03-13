package com.example.domain.usecase.WeatherDBUseCase

import com.example.domain.repo.WeatherDBRepo

class GetWeatherDBUseCase(private val weatherDBRepo: WeatherDBRepo) {
    suspend operator fun invoke()=weatherDBRepo.getAll()
}