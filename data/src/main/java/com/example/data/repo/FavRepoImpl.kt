package com.example.data.repo

import com.example.data.api.ApiService
import com.example.domain.entity.ModelDatabase.FavouriteModel
import com.example.domain.repo.FavRepo

class FavRepoImpl(private val apiService: ApiService):FavRepo {
    override suspend fun getAll(): List<FavouriteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(favouriteModel: FavouriteModel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(favouriteModel: FavouriteModel) {
        TODO("Not yet implemented")
    }
}