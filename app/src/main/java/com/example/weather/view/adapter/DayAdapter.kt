package com.example.weather.view.adapter

import android.content.Context
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.weather.R
import com.example.weather.databinding.RvDayBinding
import com.example.weather.utils.ApproximateTemp
import com.example.weather.utils.IconsApp
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class DayAdapter(var weatherResponse: WeatherResponse?) : RecyclerView.Adapter<DayAdapter. DailyViewHolder>() {

    lateinit var binding: RvDayBinding





    class  DailyViewHolder(var binding: RvDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  DailyViewHolder {
        //    var inflate=(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))as LayoutInflater
        var inflate = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RvDayBinding.inflate(inflate, parent, false)
        return  DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder:  DailyViewHolder, position: Int)  {
        if (weatherResponse != null) {
            holder.binding.tvDay.text=getDay(weatherResponse!!.daily[position].dt)
            IconsApp.getSuitableIcon(weatherResponse!!.daily[position].weather[0].icon,holder.binding.imgStatusDay)
            holder.binding.tvDescriptionDay.text=weatherResponse!!.daily[position].weather[0].description
         //   holder.binding.tvTempDay.text=(((weatherResponse!!.daily[position].temp.day)-273.15).toString())
            holder.binding.tvTempDay.text=roundTemp(((weatherResponse!!.daily[position].temp.day)-273.15)) + "\u00B0"


        }

    }

    override fun getItemCount(): Int {
        return weatherResponse?.daily?.size ?: 2
    }
    
    fun getDay(dt: Int): String {
        val cityTxtFormat = SimpleDateFormat("E")
        val cityTxtData = Date(dt.toLong() * 1000)
        return cityTxtFormat.format(cityTxtData)
    }
//---------this method to convert number like 19.7875789621245  to 19.79 ---------------------------------
    fun roundTemp(num:Double):String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

return df.format(num)
    }
}
