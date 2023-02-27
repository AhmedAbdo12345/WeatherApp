package com.example.weather.di

import com.example.data.api.ApiService
import com.example.data.repo.WeatherRepoImpl
import com.example.domain.repo.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService):WeatherRepo{
        return WeatherRepoImpl(apiService)
    }
}