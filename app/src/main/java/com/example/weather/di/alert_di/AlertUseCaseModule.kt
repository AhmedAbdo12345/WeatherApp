package com.example.weather.di.alert_di

import android.content.Context
import com.example.data.database.alert.AlertDao
import com.example.data.database.alert.AlertDatabase

import com.example.domain.repo.AlertRepo
import com.example.domain.usecase.AlertUseCase.DeleteAlertUseCase
import com.example.domain.usecase.AlertUseCase.GetAlertUseCase
import com.example.domain.usecase.AlertUseCase.InsertAlertUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AlertUseCaseModule {


    @Provides
    @Singleton
    fun provideDBInstance(@ApplicationContext context: Context): AlertDatabase {
        return AlertDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDaoAlertRepo(alertDatabase: AlertDatabase): AlertDao {
        return alertDatabase.getAlertDao()
    }


    @Provides
    fun provideInsertUseCase(alertRepo: AlertRepo): InsertAlertUseCase {
        return InsertAlertUseCase(alertRepo)
    }

    @Provides
    fun provideDeleteUseCase(alertRepo: AlertRepo): DeleteAlertUseCase {
        return DeleteAlertUseCase(alertRepo)
    }

    @Provides
    fun provideGetAlertUseCase(alertRepo: AlertRepo): GetAlertUseCase {
        return GetAlertUseCase(alertRepo)
    }
}