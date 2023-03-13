package com.example.domain.repo

import com.example.domain.entity.ModelDatabase.WeatherDB

interface WeatherDBRepo {
    suspend fun getAll(): List<WeatherDB>
    suspend fun insert(weatherDB:WeatherDB )
    suspend fun delete(weatherDB: WeatherDB)
}