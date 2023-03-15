package com.example.data.repo

import com.example.data.database.alert.AlertDatabase
import com.example.data.database.fav.FavDatabase
import com.example.data.mapper.toDataModel
import com.example.data.mapper.toDomainModel
import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.repo.AlertRepo
import com.example.domain.repo.FavRepo

class AlertRepoImpl(private val alertDatabase: AlertDatabase): AlertRepo {
    override suspend fun getAll(): List<Alert> {
        return alertDatabase.getAlertDao().getAll().map {
            it.toDomainModel()
        }    }

    override suspend fun insert(alert: Alert) {
        alertDatabase.getAlertDao().insert(alert.toDataModel())
    }

    override suspend fun delete(alert: Alert) {
        alertDatabase.getAlertDao().delete(alert.toDataModel())
    }
}