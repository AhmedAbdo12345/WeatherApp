package com.example.weather.di.weather_database_di

import android.content.Context
import com.example.data.database.weather.WeatherDao
import com.example.data.database.weather.WeatherDatabase
import com.example.domain.repo.WeatherDBRepo
import com.example.domain.usecase.WeatherDBUseCase.DeleteWeatheDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.GetWeatherDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.InsertWeatherDBUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatheDBUseCaseModule {

    @Provides
    @Singleton
    fun provideWeatherDBInstance(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.getWeatherDao()
    }


    @Provides
    fun provideInsertWeatherDBUseCase(weatherDBRepo: WeatherDBRepo): InsertWeatherDBUseCase {
        return InsertWeatherDBUseCase(weatherDBRepo)
    }

    @Provides
    fun provideDeleteWeatheDBUseCase(weatherDBRepo: WeatherDBRepo): DeleteWeatheDBUseCase {
        return DeleteWeatheDBUseCase(weatherDBRepo)
    }

    @Provides
    fun provideGetWeatherDBUseCase(weatherDBRepo: WeatherDBRepo): GetWeatherDBUseCase {
        return GetWeatherDBUseCase(weatherDBRepo)
    }
}