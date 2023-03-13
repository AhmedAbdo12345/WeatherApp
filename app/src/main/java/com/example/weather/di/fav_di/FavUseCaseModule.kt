package com.example.weather.di.fav_di

import android.content.Context
import com.example.data.database.fav.FavDao
import com.example.data.database.fav.FavDatabase
import com.example.domain.repo.FavRepo
import com.example.domain.usecase.FavUseCase.DeleteFavUseCase
import com.example.domain.usecase.FavUseCase.GetFavUseCase
import com.example.domain.usecase.FavUseCase.InsertFavUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavUseCaseModule {


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