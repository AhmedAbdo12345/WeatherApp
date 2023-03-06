package com.example.weather.di.fav_di

import android.content.Context
import com.example.data.database.FavDao
import com.example.data.database.FavDatabase
import com.example.domain.repo.FavRepo
import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.DeleteFavUseCase
import com.example.domain.usecase.GetFavUseCase
import com.example.domain.usecase.GetWeatherUseCase
import com.example.domain.usecase.InsertFavUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.sql.Timestamp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InsertUseCaseModule {


    @Provides
    @Singleton
    fun provideDBInstance(@ApplicationContext context: Context): FavDatabase {
        return FavDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDaofavRepo(favDatabase: FavDatabase): FavDao {
        return favDatabase.getFavDao()
    }


    @Provides
    fun provideInsertUseCase(favRepo: FavRepo): InsertFavUseCase {
        return InsertFavUseCase(favRepo)
    }

    @Provides
    fun provideDeleteUseCase(favRepo: FavRepo): DeleteFavUseCase {
        return DeleteFavUseCase(favRepo)
    }

    @Provides
    fun provideGetFavUseCase(favRepo: FavRepo): GetFavUseCase {
        return GetFavUseCase(favRepo)
    }
}