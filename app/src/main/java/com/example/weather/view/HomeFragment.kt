package com.example.weather.view


import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.utils.CurrentLocation
import com.example.weather.utils.CurrentLocationStatue
import com.example.weather.utils.IconsApp
import com.example.weather.utils.permissionId
import com.example.weather.view.adapter.DayAdapter
import com.example.weather.view.adapter.HourAdapter
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() , CurrentLocationStatue {
    lateinit var binding: FragmentHomeBinding
    private val viewmodel: WeatherViewModel by activityViewModels()
    lateinit var currentLocation:CurrentLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         currentLocation=CurrentLocation(requireContext(), requireActivity(),this)
        currentLocation.getLocation()
    }

    fun getDateTime(dt: Int,simpleDateFormat: SimpleDateFormat=SimpleDateFormat("E,d MMM ',' hh:mm aa")): String? {
       // val cityTxtFormat = SimpleDateFormat("E,d MMM ',' hh:mm aa")

        val cityTxtData = Date(dt.toLong() * 1000)
        return simpleDateFormat.format(cityTxtData)
    }

    //---------------------------------------------------------------

    //---------------------------------------------------------------
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
             currentLocation.getLocation()

            }
        }
    }

    override fun success(list: List<Address>) {
        getDataFromNetwork(list[0].latitude,list[0].longitude,list[0].adminArea)

    }

    override fun failed(msg: String) {
        Log.i("zxcv", "failed: ${msg.toString()}")

    }
    fun getDataFromNetwork(lat:Double,lon:Double,city:String){
        viewmodel.getWeather(lat, lon, "471513ea69403129f79bbd3675cfccf3")

        lifecycleScope.launch {
            viewmodel.weather.collect {
                if (it !=null) {

                    displayHourlyRecycleView(it)
                    //--------------------------------------------------------------------------------------
                    displayDailyRecycleView(it)
                    //--------------------------------------------------------------------------------------
                    displayWeatherInfo(it,city)
                }
            }
        }
    }

    fun displayHourlyRecycleView(it:WeatherResponse){
        var hourlyAdapter = HourAdapter(it)
        binding.rvHour.apply {
            adapter = hourlyAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1).apply {
                orientation = RecyclerView.HORIZONTAL
            }
        }
    }
    fun displayDailyRecycleView(it:WeatherResponse){
        var dailyAdapter = DayAdapter(it)
        binding.rvDay.apply {
            adapter = dailyAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }
    fun displayWeatherInfo(wr: WeatherResponse,city: String){
        binding.tvCity.text = city
        binding.tvDate.text = getDateTime(wr.current.dt)
        IconsApp.getSuitableIcon(wr.current.weather[0].icon, binding.imgStatusWeather)
        binding.tvCurrentTemp.text = ((wr.current.temp).minus(273.15).toString())
        binding.tvDescription.text = wr.current.weather.get(0).description
        //---------------------------------------------------------------------------------------

        binding.tvPressure.text=wr.current.pressure.toString()+" hpa"
        binding.tvHumidity.text=wr.current.humidity.toString()+" %"
        binding.tvWind.text=wr.current.wind_speed.toString()+" m/s"

        binding.tvCloud.text=wr.current.clouds.toString()+" %"
        binding.tvUvi.text=wr.current.uvi.toString()
        binding.tvVisibility.text=wr.current.visibility.toString()+" m"

        binding.tvTemperature.text=(wr.current.temp).minus(273.15).toString()
        binding.tvSunRise.text=getDateTime(wr.current.sunrise,SimpleDateFormat("hh:mm aa"))

        binding.tvSunSet.text=getDateTime(wr.current.sunset,SimpleDateFormat("hh:mm aa"))

    }
}