package com.example.data.database

import androidx.room.*
import com.example.domain.entity.ModelDatabase.FavouriteModel

@Dao
interface FavDao {
    @Query("SELECT * FROM FavouriteModel")
    suspend fun getAll(): List<FavouriteModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouriteModel: FavouriteModel)

    @Delete
    suspend fun delete(favouriteModel: FavouriteModel)
}