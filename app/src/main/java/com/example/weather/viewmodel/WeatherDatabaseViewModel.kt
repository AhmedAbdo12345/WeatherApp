package com.example.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.fav.FavouriteModel
import com.example.data.database.weather.WeatherDBModel
import com.example.data.mapper.toDomainModel
import com.example.data.mapper.toListDataModel
import com.example.domain.entity.ModelDatabase.WeatherDB
import com.example.domain.usecase.WeatherDBUseCase.DeleteWeatheDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.GetWeatherDBUseCase
import com.example.domain.usecase.WeatherDBUseCase.InsertWeatherDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDatabaseViewModel @Inject constructor(
    private val insertWeatherDBUseCase: InsertWeatherDBUseCase,
    private val deleteWeatherDBUseCase: DeleteWeatheDBUseCase,
    private val getWeatherDBUseCase: GetWeatherDBUseCase
) : ViewModel() {
    private var _allWeatherFromRoom: MutableStateFlow<List<WeatherDBModel>?> = MutableStateFlow(null)
    val allWeatherFromRoom: StateFlow<List<WeatherDBModel>?> = _allWeatherFromRoom


    fun insertWeatherInRoom(weatherDBModel: WeatherDBModel) {
        viewModelScope.launch {
            try {
                insertWeatherDBUseCase(weatherDBModel.toDomainModel())

            } catch (e: Exception) {
                Log.e("FavouriteViewModel", e.message.toString())

            }
        }
    }

    fun deleteWeatherFromRoom(weatherDBModel: WeatherDBModel) {
        viewModelScope.launch {
            try {
                deleteWeatherDBUseCase(weatherDBModel.toDomainModel())

            } catch (e: Exception) {
                Log.e("WeatherDBViewModel", e.message.toString())

            }
        }
    }
    fun getAllWeatherFromRoom() {
        viewModelScope.launch {
            try {
                var listWeatherDB:List<WeatherDB> = getWeatherDBUseCase()
                _allWeatherFromRoom.value= listWeatherDB.toListDataModel()

            } catch (e: Exception) {
                Log.e("WeatherDBViewModel", e.message.toString())

            }
        }
    }
}