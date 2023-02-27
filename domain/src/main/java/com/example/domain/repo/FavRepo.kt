package com.example.domain.repo

import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.domain.entity.ModelDatabase.FavouriteModel
import java.sql.Timestamp


interface FavRepo {
    suspend fun getAll(): List<FavouriteModel>
    suspend fun insert(favouriteModel: FavouriteModel)
    suspend fun delete(favouriteModel: FavouriteModel)
}