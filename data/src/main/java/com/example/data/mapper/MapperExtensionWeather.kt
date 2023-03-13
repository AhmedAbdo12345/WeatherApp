package com.example.data.mapper

import com.example.data.database.weather.WeatherDBModel
import com.example.domain.entity.ModelDatabase.WeatherDB

fun WeatherDBModel?.toDomainModel(): WeatherDB {
    return WeatherDB(
        this!!.id,
        this.city,
        this.current ,
        this.daily,
        this.hourly,
        this.lat,
        this.lon,
        this.minutely!!,
        this.timezone,
        this.timezone_offset
    )
}


fun WeatherDB?.toDataModel(): WeatherDBModel {
    return WeatherDBModel(
        this!!.id,
        this.city,
        this.current ,
        this.daily,
        this.hourly,
        this.lat,
        this.lon,
        this.minutely,
        this.timezone,
        this.timezone_offset
    )
}
fun List<WeatherDB>?.toListDataModel(): List<WeatherDBModel> {
    var list= mutableListOf<WeatherDBModel>()
    for(i in 0 until (this?.size ?: 0)){
        list.add(WeatherDBModel(
            this!![i].id,
            this[i].city,
            this[i].current ,
            this[i].daily,
            this[i].hourly,
            this[i].lat,
            this[i].lon,
            this[i].minutely,
            this[i].timezone,
            this[i].timezone_offset
        )
        )
    }
    return list
}