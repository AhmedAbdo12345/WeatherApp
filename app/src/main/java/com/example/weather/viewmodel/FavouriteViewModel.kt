package com.example.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.FavouriteModel
import com.example.data.mapper.toDomainModel
import com.example.data.mapper.toListDataModel
import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.usecase.DeleteFavUseCase
import com.example.domain.usecase.GetFavUseCase
import com.example.domain.usecase.GetWeatherUseCase
import com.example.domain.usecase.InsertFavUseCase
import com.example.weather.utils.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val insertFavUseCase: InsertFavUseCase,
    private val deleteFavUseCase: DeleteFavUseCase,
    private val getFavUseCase: GetFavUseCase
) : ViewModel() {
    private var _allFavourites:MutableStateFlow<List<FavouriteModel>?> = MutableStateFlow(null)
    val allFavourites:StateFlow<List<FavouriteModel>?> = _allFavourites


    fun insertFavourite(favouritModel: FavouriteModel) {
        viewModelScope.launch {
            try {
                insertFavUseCase(favouritModel.toDomainModel())

                //   _weather.emit(ApiStatus.Success(getWeatherUseCase(lat,lon,apiKey)))
            } catch (e: Exception) {
                Log.e("FavouriteViewModel", e.message.toString())

            }
        }
    }

    fun deleteFavourite(favouritModel: FavouriteModel) {
        viewModelScope.launch {
            try {
                deleteFavUseCase(favouritModel.toDomainModel())

            } catch (e: Exception) {
                Log.e("FavouriteViewModel", e.message.toString())

            }
        }
    }
    fun getAllFavourite() {
        viewModelScope.launch {
            try {
                var listFavourite:List<Favourite> = getFavUseCase()
                _allFavourites.value= listFavourite.toListDataModel()

            } catch (e: Exception) {
                Log.e("FavouriteViewModel", e.message.toString())

            }
        }
    }
}