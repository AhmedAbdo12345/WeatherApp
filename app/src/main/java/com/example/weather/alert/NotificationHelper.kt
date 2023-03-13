package com.example.weather.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.activityViewModels
import com.example.weather.R
import com.example.weather.view.MainActivity
import com.example.weather.viewmodel.WeatherViewModel

const val CHANNEL_ID = "19"
class NotificationHelper(var context: Context) {

    fun createNotification(title:String,content:String){
        createNotificationChannel()
        val intent= Intent(context, MainActivity::class.java)
        val pendingIntent= PendingIntent.getActivity(context,0,intent,0)

        var builder: NotificationCompat.Builder=
            NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.sun_cloud)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManagerCompat= NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(10,builder.build())
    }


     private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Display Weather",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My Channel Weather"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}