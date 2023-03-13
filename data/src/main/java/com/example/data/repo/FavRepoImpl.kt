package com.example.data.repo

import com.example.data.database.fav.FavDatabase
import com.example.data.database.fav.FavouriteModel
import com.example.data.mapper.toDataModel
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.repo.FavRepo

class FavRepoImpl(private val favDatabase: FavDatabase):FavRepo {
    override suspend fun getAll(): List<Favourite> {
        return favDatabase.getFavDao().getAll().map {
            it.toDomainModel()
        }

    }

    override suspend fun insert(favourite: Favourite) {
       // favDatabase.getFavDao().insert(FavouriteModel(favourite.city,favourite.latitude,favourite.longitude))
        favDatabase.getFavDao().insert(favourite.toDataModel())

    }

    override suspend fun delete(favourite: Favourite) {
        favDatabase.getFavDao().delete(favourite.toDataModel())
    }

}