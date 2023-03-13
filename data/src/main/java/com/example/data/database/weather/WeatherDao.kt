package com.example.data.database.weather

import androidx.room.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherDBModel")
    suspend fun getAll(): List<WeatherDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherDBModel: WeatherDBModel)

    @Delete
    suspend fun delete(weatherDBModel: WeatherDBModel)
}