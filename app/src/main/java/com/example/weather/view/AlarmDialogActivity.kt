package com.example.weather.view

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.data.utils.Constants
import com.example.weather.R
import com.example.weather.databinding.ActivityAlarmDialogBinding
import com.example.weather.utils.ApiStatus
import com.example.weather.utils.ApproximateTemp
import com.example.weather.utils.IconsApp
import com.example.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class AlarmDialogActivity : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null


lateinit var binding:ActivityAlarmDialogBinding
    private val viewmodel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //setContentView(R.layout.activity_alarm_dialog)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_dialog)

        var  sharedPreference =getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var approximateTemp= ApproximateTemp()

        var city= sharedPreference.getString(Constants.CityName,"").toString()
        var lat= sharedPreference.getString(Constants.Latitude,"0.0")
        var lng= sharedPreference.getString(Constants.Longitude,"0.0")
        var language= sharedPreference.getString(Constants.Language, "en").toString()

        getDataFromNetwork(lat?.toDouble() ?: 0.0, lng?.toDouble() ?: 0.0,language,city)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setFinishOnTouchOutside(false)


    }

    private fun startSound() {
        if (mMediaPlayer == null) { //mMediaPkayer is your variable
            //mMediaPlayer = MediaPlayer.create(this, R.raw.weather_sound2) //raw is the folder where you have the audio files or sounds, water is the audio file (is a example right)
            mMediaPlayer!!.isLooping = true //to repeat again n again
            mMediaPlayer!!.start() //to start the sound
        }
    }

    private fun handleUI() {

    }

    fun getDataFromNetwork(lat: Double, lon: Double,lang:String, city: String) {
        viewmodel.getWeather(lat, lon,lang, getString(R.string.API_KEY2))

        lifecycleScope.launch {
            viewmodel.weather.collect {

                when (it) {
                    is ApiStatus.Success -> {
                        binding.tvAlarmCity.text=city
                        binding.tvAlarmDescription.text=it.weatherResponse.current.weather.get(0).description
                        IconsApp.getSuitableIcon(it.weatherResponse.current.weather[0].icon, binding.imgAlarmIcon)
                    }
                    is ApiStatus.Loading -> {

                    }
                    else -> {}
                }

            }
        }
    }

}