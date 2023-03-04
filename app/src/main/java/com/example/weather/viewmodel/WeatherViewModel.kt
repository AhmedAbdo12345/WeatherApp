package com.example.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherUseCase:GetWeatherUseCase) :ViewModel(){

    private var _weather: MutableStateFlow<WeatherResponse?> = MutableStateFlow(null)
    val weather: StateFlow<WeatherResponse?> =_weather

    fun getWeather(lat:Double, lon:Double, apiKey:String){
        viewModelScope.launch {
            try {
                _weather.value=getWeatherUseCase(lat,lon,apiKey)
            }catch (e:Exception){
                Log.e("MealsViewModel", e.message.toString())

            }
        }
    }
}