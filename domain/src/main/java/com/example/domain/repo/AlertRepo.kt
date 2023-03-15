package com.example.domain.repo

import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.entity.ModelDatabase.Favourite

interface AlertRepo {
    suspend fun getAll(): List<Alert>
    suspend fun insert(alert: Alert)
    suspend fun delete(alert: Alert)
}