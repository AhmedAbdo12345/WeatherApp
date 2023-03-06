package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteModel(@PrimaryKey val city:String,val latitude:Double,val longitude:Double):java.io.Serializable