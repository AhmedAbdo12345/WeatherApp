package com.example.data.repo

import com.example.data.database.weather.WeatherDatabase
import com.example.data.mapper.toDataModel
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelDatabase.WeatherDB
import com.example.domain.repo.WeatherDBRepo

class WeatherDBRepoImpl (private val weatherDatabase: WeatherDatabase): WeatherDBRepo {
    override suspend fun getAll(): List<WeatherDB> {
        return weatherDatabase.getWeatherDao().getAll().map {
            it.toDomainModel()
        }
    }

    override suspend fun insert(weatherDB: WeatherDB) {
        weatherDatabase.getWeatherDao().insert(weatherDB.toDataModel())
    }

    override suspend fun delete(weatherDB: WeatherDB) {
        weatherDatabase.getWeatherDao().delete(weatherDB.toDataModel())
    }


}