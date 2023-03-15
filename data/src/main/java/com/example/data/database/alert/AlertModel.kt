package com.example.data.database.alert

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class AlertModel(@PrimaryKey var id:String, var timeStart:String, val timeEnd:String, val dateStart:String, val dateEnd:String)