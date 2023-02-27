package com.example.weather.di

import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.sql.Timestamp

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(weatherRepo: WeatherRepo): GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepo)
    }
}