package com.example.data.mapper

import com.example.data.database.FavouriteModel
import com.example.data.utils.Constants
import com.example.domain.entity.ModelDatabase.Favourite

fun FavouriteModel?.toDomainModel(): Favourite {
    return Favourite(
        this?.latitude?.orZero() ?: Constants.zero,
        this?.longitude.orZero() ?: Constants.zero,
        this?.city?.orEmpty()?:Constants.emptyString
    )
}

fun Double?.orZero(): Double {
    return this ?: Constants.zero
}

fun Int?.orZero(): Int {
    return this ?: Constants.zero.toInt()
}