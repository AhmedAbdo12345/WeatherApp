package com.example.data.database.fav

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(FavouriteModel::class), version = 1 )
abstract class FavDatabase : RoomDatabase() {
    abstract fun getFavDao(): FavDao
    companion object{
        @Volatile
        private var INSTANCE: FavDatabase? = null
        fun getInstance (ctx: Context): FavDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, FavDatabase::class.java, "Favourite_database")
                    .build()
                INSTANCE = instance
              // return instance
                instance }
        }
    }
}