package com.example.weather.di.fav_di

import com.example.data.database.FavDatabase
import com.example.data.repo.FavRepoImpl
import com.example.domain.repo.FavRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FavRepoModule {

    @Provides
    fun provideRepo(favDatabase: FavDatabase):FavRepo{
        return FavRepoImpl(favDatabase)
    }
}