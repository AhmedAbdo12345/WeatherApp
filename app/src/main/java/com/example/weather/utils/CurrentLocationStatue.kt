package com.example.weather.utils

import android.location.Address

interface CurrentLocationStatue {
    fun success(list :List<Address>)
}