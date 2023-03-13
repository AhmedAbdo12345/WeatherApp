package com.example.weather.di.weather_database_di

import com.example.data.database.fav.FavDatabase
import com.example.data.database.weather.WeatherDatabase
import com.example.data.repo.FavRepoImpl
import com.example.data.repo.WeatherDBRepoImpl
import com.example.domain.repo.FavRepo
import com.example.domain.repo.WeatherDBRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherDBRepoModule {

    @Provides
    fun provideRepo(weatherDatabase: WeatherDatabase): WeatherDBRepo {
        return WeatherDBRepoImpl(weatherDatabase)
    }
}