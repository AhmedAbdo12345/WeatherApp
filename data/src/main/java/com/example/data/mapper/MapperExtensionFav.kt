package com.example.data.mapper

import com.example.data.database.fav.FavouriteModel
import com.example.data.utils.Constants
import com.example.domain.entity.ModelDatabase.Favourite

fun FavouriteModel?.toDomainModel(): Favourite {
    return Favourite(
        this?.city?.orEmpty()?:Constants.emptyString,
        this?.latitude?.orZero() ?: Constants.zero,
        this?.longitude.orZero() ?: Constants.zero
    )
}



fun Favourite?.toDataModel(): FavouriteModel {
    return FavouriteModel(
        this?.city?.orEmpty()?:Constants.emptyString,
        this?.latitude?.orZero() ?: Constants.zero,
        this?.longitude.orZero() ?: Constants.zero
    )
}

fun Double?.orZero(): Double {
    return this ?: Constants.zero
}

fun Int?.orZero(): Int {
    return this ?: Constants.zero.toInt()
}

fun List<Favourite>?.toListDataModel(): List<FavouriteModel> {
    var list= mutableListOf<FavouriteModel>()
    for(i in 0 until (this?.size ?: 0)){
        list.add(FavouriteModel(this?.get(i)?.city.toString(), this?.get(i)?.latitude!!,this?.get(i)?.longitude))
    }
    return list
}

