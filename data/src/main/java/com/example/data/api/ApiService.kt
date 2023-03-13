package com.example.data.api

import com.example.domain.entity.ModelApi.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Timestamp


interface ApiService {
    @GET("onecall")
    suspend fun getWeather(@Query("lat")lat:Double,@Query("lon")lon:Double,@Query("lang")lang:String,@Query("appid")apiKey:String): WeatherResponse

}