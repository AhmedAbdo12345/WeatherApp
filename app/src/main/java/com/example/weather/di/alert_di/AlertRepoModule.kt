package com.example.weather.di.alert_di

import com.example.data.database.alert.AlertDatabase
import com.example.data.database.fav.FavDatabase
import com.example.data.repo.AlertRepoImpl
import com.example.data.repo.FavRepoImpl
import com.example.domain.repo.AlertRepo
import com.example.domain.repo.FavRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AlertRepoModule {

    @Provides
    fun provideRepo(alertDatabase: AlertDatabase): AlertRepo {
        return AlertRepoImpl(alertDatabase)
    }
}