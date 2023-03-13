package com.example.data.database.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.ModelApi.Current
import com.example.domain.entity.ModelApi.Daily
import com.example.domain.entity.ModelApi.Hourly
import com.example.domain.entity.ModelApi.Minutely

@Entity
data class WeatherDBModel(
    @PrimaryKey
    val id:Int,
    val city:String,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>?,
    val timezone: String,
    val timezone_offset: Int
):java.io.Serializable