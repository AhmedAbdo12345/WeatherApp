package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteModel(val latitude:Double,val longitude:Double,@PrimaryKey val city:String):java.io.Serializable