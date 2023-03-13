package com.example.data.database.fav

import androidx.room.*

@Dao
interface FavDao {
    @Query("SELECT * FROM FavouriteModel")
    suspend fun getAll(): List<FavouriteModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouriteModel: FavouriteModel)

    @Delete
    suspend fun delete(favouriteModel: FavouriteModel)
}