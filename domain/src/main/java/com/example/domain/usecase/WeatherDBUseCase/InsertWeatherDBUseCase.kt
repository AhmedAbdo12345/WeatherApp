package com.example.domain.usecase.WeatherDBUseCase

import com.example.domain.entity.ModelDatabase.WeatherDB
import com.example.domain.repo.WeatherDBRepo


class InsertWeatherDBUseCase(private val weatherDBRepo: WeatherDBRepo) {
    suspend operator fun invoke(weatherDB: WeatherDB)=weatherDBRepo.insert(weatherDB)}