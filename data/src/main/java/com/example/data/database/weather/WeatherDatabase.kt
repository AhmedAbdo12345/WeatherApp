package com.example.data.database.weather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.fav.FavDao
import com.example.data.database.fav.FavouriteModel
import com.example.data.utils.ConverterDB


@Database(entities = arrayOf(WeatherDBModel::class), version = 1 )
@TypeConverters(ConverterDB::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    companion object{
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance (ctx: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDatabase::class.java, "Weather_database")
                    .build()
                INSTANCE = instance
                // return instance
                instance }
        }
    }
}