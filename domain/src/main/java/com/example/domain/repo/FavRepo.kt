package com.example.domain.repo

import com.example.domain.entity.ModelDatabase.Favourite


interface FavRepo {
    suspend fun getAll(): List<Favourite>
    suspend fun insert(favouriteModel: Favourite)
    suspend fun delete(favouriteModel: Favourite)
}