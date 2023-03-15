package com.example.data.database.alert

import androidx.room.*
import com.example.data.database.fav.FavouriteModel

@Dao
interface AlertDao {
    @Query("SELECT * FROM AlertModel")
    suspend fun getAll(): List<AlertModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alertModel: AlertModel)

    @Delete
    suspend fun delete(alertModel: AlertModel)
}