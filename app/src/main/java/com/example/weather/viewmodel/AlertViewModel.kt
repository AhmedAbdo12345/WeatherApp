package com.example.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.alert.AlertModel
import com.example.data.mapper.toDomainModel
import com.example.data.mapper.toListDataModel
import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.entity.ModelDatabase.Favourite
import com.example.domain.usecase.AlertUseCase.InsertAlertUseCase
import com.example.domain.usecase.AlertUseCase.DeleteAlertUseCase
import com.example.domain.usecase.AlertUseCase.GetAlertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val insertAlertUseCase: InsertAlertUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
    private val getAlertUseCase: GetAlertUseCase
) : ViewModel() {
    private var _allAlert: MutableStateFlow<List<AlertModel>?> = MutableStateFlow(null)
    val allAlert: StateFlow<List<AlertModel>?> = _allAlert


    fun insertAlertModel(alertModel: AlertModel) {
        viewModelScope.launch {
            try {
                insertAlertUseCase(alertModel.toDomainModel())

            } catch (e: Exception) {
                Log.e("AlertViewModel", e.message.toString())

            }
        }
    }

    fun deleteAlertModel(alertModel: AlertModel) {
        viewModelScope.launch {
            try {
                deleteAlertUseCase(alertModel.toDomainModel())

            } catch (e: Exception) {
                Log.e("FavouriteViewModel", e.message.toString())

            }
        }
    }
    fun getAlertFromRoom() {
        viewModelScope.launch {
            try {
                var listAlert:List<Alert> = getAlertUseCase()
                _allAlert.value= listAlert.toListDataModel()

            } catch (e: Exception) {
                Log.e("AlertViewModel", e.message.toString())

            }
        }
    }
}