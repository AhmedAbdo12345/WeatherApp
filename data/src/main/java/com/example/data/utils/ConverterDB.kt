package com.example.data.utils

import androidx.room.TypeConverter
import com.example.data.database.weather.WeatherDBModel
import com.example.domain.entity.ModelApi.Current
import com.example.domain.entity.ModelApi.Daily
import com.example.domain.entity.ModelApi.Hourly
import com.example.domain.entity.ModelApi.Minutely
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConverterDB {
    @TypeConverter
    fun convertWeatherDBModelToString(weatherDBModel: WeatherDBModel):String{
        return Gson().toJson(weatherDBModel)
    }

    @TypeConverter
    fun convertStringToWeatherDBModel(weatherString: String):WeatherDBModel{
        return Gson().fromJson(weatherString,WeatherDBModel::class.java)
    }

//-----------------------------------------------------------------
    @TypeConverter
    fun convertCurrentToString(current: Current):String{
        return Gson().toJson(current)
    }

    @TypeConverter
    fun convertStringToCurrent(currentString: String):Current{
        return Gson().fromJson(currentString,Current::class.java)
    }
//---------------------------------------------------
    @TypeConverter
    fun convertListDailyToString(listDaily: List<Daily>):String{
        return Gson().toJson(listDaily)
    }

    @TypeConverter
    fun convertStringToListDaily(listDailyString: String):List<Daily>{
        val type:Type=object :TypeToken<List<Daily>>(){}.type
        return Gson().fromJson(listDailyString,type)
    }
    //---------------------------------------------------
    @TypeConverter
    fun convertListHouryToString(listHourly: List<Hourly>):String{
        return Gson().toJson(listHourly)
    }

    @TypeConverter
    fun convertStringToListHourly(listHourlyString: String):List<Hourly>{
        val type:Type=object :TypeToken<List<Hourly>>(){}.type
        return Gson().fromJson(listHourlyString,type)
    }
    //---------------------------------------------------
    @TypeConverter
    fun convertListMinutelyToString(listMinutely: List<Minutely>):String{
        return Gson().toJson(listMinutely)
    }

    @TypeConverter
    fun convertStringToListMinutely(listMinutelyString: String):List<Minutely>{
        val type:Type=object :TypeToken<List<Minutely>>(){}.type
        return Gson().fromJson(listMinutelyString,type)
    }
    //---------------------------------------------------
}