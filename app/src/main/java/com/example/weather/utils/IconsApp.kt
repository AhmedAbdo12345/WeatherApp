package com.example.weather.utils

import android.widget.ImageView
import com.example.weather.R

class IconsApp {
    companion object {
        fun getSuitableIcon(img: String, imgView: ImageView) {
            when (img) {
                "01d" -> imgView.setImageResource(R.drawable.sun)
                "01n" -> imgView.setImageResource(R.drawable.moon2)

                "02d" -> imgView.setImageResource(R.drawable.sun_cloud)
                "02n" -> imgView.setImageResource(R.drawable.cloud_night)

                "03d" -> imgView.setImageResource(R.drawable.cloud)
                "03n" -> imgView.setImageResource(R.drawable.cloud)

                "04d" -> imgView.setImageResource(R.drawable.broken_cloud)
                "04n" -> imgView.setImageResource(R.drawable.broken_cloud)

                "09d" -> imgView.setImageResource(R.drawable.rain)
                "09n" -> imgView.setImageResource(R.drawable.rain)

                "10d" -> imgView.setImageResource(R.drawable.sun_cloud_rain)
                "10n" -> imgView.setImageResource(R.drawable.night_cloud_rain)

                "11d" -> imgView.setImageResource(R.drawable.storm)
                "11n" -> imgView.setImageResource(R.drawable.storm)

                "13d" -> imgView.setImageResource(R.drawable.storm)
                "13n" -> imgView.setImageResource(R.drawable.storm)

                "50d" -> imgView.setImageResource(R.drawable.mist)
                "50n" -> imgView.setImageResource(R.drawable.mist)
                else -> print(" ")
            }
        }
    }
}