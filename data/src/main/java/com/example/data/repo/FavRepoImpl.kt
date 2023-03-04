package com.example.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.api.ApiService
import com.example.data.database.FavDao
import com.example.data.database.FavDatabase
import com.example.data.database.FavouriteModel
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.repo.FavRepo
import com.google.gson.annotations.Until
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavRepoImpl(private val favDatabase: FavDatabase):FavRepo {
    override suspend fun getAll(): List<Favourite> {
        return favDatabase.getFavDao().getAll().map {
            it.toDomainModel()
        }

    }

    override suspend fun insert(favourite: Favourite) {
        favDatabase.getFavDao().insert(FavouriteModel(favourite.latitude,favourite.longitude,favourite.city))
    }

    override suspend fun delete(favourite: Favourite) {
        favDatabase.getFavDao().delete(FavouriteModel(favourite.latitude,favourite.longitude,favourite.city))
    }

}