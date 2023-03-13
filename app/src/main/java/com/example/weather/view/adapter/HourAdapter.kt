package com.example.weather.view.adapter

import android.content.Context
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.weather.R
import com.example.weather.databinding.RvHourTempBinding
import com.example.weather.utils.ApproximateTemp
import com.example.weather.utils.IconsApp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class HourAdapter(var weatherResponse: WeatherResponse?) :
    RecyclerView.Adapter<HourAdapter.HourlyViewHolder>() {

    lateinit var binding: RvHourTempBinding


    class HourlyViewHolder(var binding: RvHourTempBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        //    var inflate=(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))as LayoutInflater
        var inflate =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RvHourTempBinding.inflate(inflate, parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        var approximateTemp = ApproximateTemp()
        if (weatherResponse != null) {
            holder.binding.tvHour.text =
                getCurrentTime(weatherResponse!!.hourly[position].dt, weatherResponse!!.timezone)
            holder.binding.tvTemp.text =
                approximateTemp((weatherResponse!!.hourly[position].temp) - 273.15) + "\u00B0"
            var iconForHourName =
                weatherResponse?.hourly?.get(position)?.weather?.get(0)?.icon.toString()

            IconsApp.getSuitableIcon(iconForHourName, holder.binding.imgStatusHour)

        }

    }

    override fun getItemCount(): Int {
        return weatherResponse?.hourly?.size ?: 2
    }

    fun getCurrentTime(dt: Int, timezone: String, format: String = "K:mm a"): String {

        val zoneId = ZoneId.of(timezone)
        val instant = Instant.ofEpochSecond(dt.toLong())
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }
}
