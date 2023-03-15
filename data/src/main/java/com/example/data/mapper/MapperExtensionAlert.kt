package com.example.data.mapper

import com.example.data.database.alert.AlertModel
import com.example.data.database.fav.FavouriteModel
import com.example.data.utils.Constants
import com.example.domain.entity.ModelDatabase.Alert
import com.example.domain.entity.ModelDatabase.Favourite

fun AlertModel?.toDomainModel(): Alert {
    return Alert(
        this!!.id.toString(),
        this?.timeStart.orEmpty()?: Constants.emptyString,
        this?.timeEnd.orEmpty()?: Constants.emptyString,
        this?.dateStart.orEmpty()?: Constants.emptyString,
        this?.dateEnd.orEmpty()?: Constants.emptyString

    )
}



fun Alert?.toDataModel(): AlertModel {
    return AlertModel(
        this!!.id.toString(),
        this.timeStart.orEmpty()?: Constants.emptyString,
        this.timeEnd.orEmpty()?: Constants.emptyString,
        this.dateStart.orEmpty()?: Constants.emptyString,
        this.dateEnd.orEmpty()?: Constants.emptyString

    )
}

fun List<Alert>?.toListDataModel(): List<AlertModel> {
    var list= mutableListOf<AlertModel>()
    for(i in 0 until (this?.size ?: 0)){
        list.add(AlertModel(this!![i].id.toString(), this[i].timeStart,this[i].timeEnd,this[i].dateStart,this[i].dateEnd))
    }
    return list
}