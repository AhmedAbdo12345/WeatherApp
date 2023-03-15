package com.example.data.database.alert

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.fav.FavDao
import com.example.data.database.fav.FavouriteModel


@Database(entities = arrayOf(AlertModel::class), version = 1 )
abstract class AlertDatabase : RoomDatabase() {
    abstract fun getAlertDao(): AlertDao
    companion object{
        @Volatile
        private var INSTANCE: AlertDatabase? = null
        fun getInstance (ctx: Context): AlertDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, AlertDatabase::class.java, "Alert_database")
                    .build()
                INSTANCE = instance
                // return instance
                instance }
        }
    }
}