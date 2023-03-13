package com.example.weather.utils

import com.example.domain.entity.ModelDatabase.Favourite
import java.math.RoundingMode
import java.text.DecimalFormat

 class ApproximateTemp(){
     operator fun invoke(temp:Double): String=approximate(temp)

    //---------this method to convert number like 19.7875789621245  to 19.79 ---------------------------------
    fun approximate(num:Double):String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return df.format(num)
    }


}