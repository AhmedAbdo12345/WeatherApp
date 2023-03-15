package com.example.weather.alert

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.util.splitToIntList
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.utils.Constants
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.domain.repo.WeatherRepo
import com.example.domain.usecase.GetWeatherUseCase
import com.example.weather.di.NetworkModule
import com.example.weather.utils.ApproximateTemp
import com.example.weather.view.AlarmDialogActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class AlertWorker(val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
   var  sharedPreference =context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    var approximateTemp= ApproximateTemp()

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {



        if (inputData.getString("typeAlert").toString().equals("Notification")) {

            var city= sharedPreference.getString(Constants.CityName,"").toString()
            var lat= sharedPreference.getString(Constants.Latitude,"0.0")
            var lng= sharedPreference.getString(Constants.Longitude,"0.0")
            var language= sharedPreference.getString(Constants.Language, "en").toString()

            var response = getWeatherStatueForAlert(lat?.toDouble() ?: 0.0,
                lng?.toDouble() ?: 0.0,language)
            var contentNotification=(response.current?.weather?.get(0)?.description)+"  "+approximateTemp(
                (response.current!!.temp)!!.minus(273.15))+"\u00B0"

            NotificationHelper(context).createNotification(
                title = city,
               content = contentNotification,
            )

        } else {
            fireAlarmDialog()
        }

        // val outputData = workDataOf("is_success" to "isSuccess")
        // return Result.success(outputData)
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fireAlarmDialog() {
        if (Settings.canDrawOverlays(context)) {
            val intent = Intent(context, AlarmDialogActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_INCLUDE_STOPPED_PACKAGES or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)

        }
    }

   /* fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return sdf.format(Date())
    }*/


    suspend fun getWeatherStatueForAlert(lat:Double,lng:Double,lang:String): WeatherResponse {
        val http = NetworkModule.provideOkHttp()
        val retrofit = NetworkModule.provideRetrofit(http)
        val apiService = NetworkModule.provideApiService(retrofit)
        var response = apiService.getWeather(
            lat,
            lng,
            lang,
            "471513ea69403129f79bbd3675cfccf3"
        )
        return response

    }
}