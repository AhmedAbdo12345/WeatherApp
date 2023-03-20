package com.example.weather.view


import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.weather.WeatherDBModel
import com.example.data.utils.Constants
import com.example.domain.entity.ModelApi.Minutely
import com.example.domain.entity.ModelApi.WeatherResponse
import com.example.weather.BuildConfig
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.utils.*
import com.example.weather.view.adapter.DayAdapter
import com.example.weather.view.adapter.HourAdapter
import com.example.weather.viewmodel.WeatherDatabaseViewModel
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

//

class HomeFragment : Fragment(), CurrentLocationStatue {


    lateinit var binding: FragmentHomeBinding
    private val viewmodel: WeatherViewModel by activityViewModels()
    private val viewmodelDatabase: WeatherDatabaseViewModel by activityViewModels()

    lateinit var currentLocation: CurrentLocation
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var currentLanguage: String = "en"
    var tempUnit=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()


        currentLanguage = sharedPreference.getString(Constants.Language, "en").toString()
        checkLanguage(currentLanguage)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
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

        tempUnit= sharedPreference.getString(Constants.Temperature,Constants.Celsius).toString()
        Log.i("zxcv", "tem123: $tempUnit")
        var locationType = sharedPreference.getString(Constants.Location, "GPS")

        var model = HomeFragmentArgs.fromBundle(requireArguments()).favModel
        if (model != null) {
            getDataFromNetwork(model.latitude, model.longitude, currentLanguage, model.city)
        } else {
            if (locationType.equals("Map")) {
                var city = sharedPreference.getString(Constants.CityName, "")
                var lat = sharedPreference.getString(Constants.Latitude, "0.0")
                var long = sharedPreference.getString(Constants.Longitude, "0.0")
                getDataFromNetwork(
                    lat?.toDouble() ?: 0.0,
                    long?.toDouble() ?: 0.0,
                    currentLanguage,
                    city!!
                )

            } else {
                currentLocation =
                    CurrentLocation(requireContext(), requireActivity(), this, currentLanguage)
                currentLocation.getLocation()
            }


        }

        binding.btnTryAgain.setOnClickListener() {

            NavHostFragment.findNavController(this@HomeFragment)
                .navigate(R.id.action_homeFragment_self)
        }
    }

    fun getDateTime(
        dt: Int,
        simpleDateFormat: SimpleDateFormat = SimpleDateFormat("E,d MMM ',' hh:mm aa")
    ): String? {
        // val cityTxtFormat = SimpleDateFormat("E,d MMM ',' hh:mm aa")

        val cityTxtData = Date(dt.toLong() * 1000)
        return simpleDateFormat.format(cityTxtData)
    }

    //---------------------------------------------------------------

    //---------------------------------------------------------------
   override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            Log.i("zxcv", "onRequestPermissionsResult:33333333 ")

            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                currentLocation.getLocation()
                Log.i("zxcv", "onRequestPermissionsResult:22222 ")

            }else{
                Log.i("zxcv", "onRequestPermissionsResult:111111111111 ")
            }
        }
    }

            override fun success(list: List<Address>) {

        getDataFromNetwork(list[0].latitude, list[0].longitude, currentLanguage, list[0].adminArea)

        editor.putString(Constants.Location, "GPS")
        editor.putString(Constants.CityName, list[0].adminArea)
        editor.putString(Constants.Latitude, (list[0].latitude).toString())
        editor.putString(Constants.Longitude, (list[0].longitude).toString())
        editor.commit()

    }


    fun getDataFromNetwork(lat: Double, lon: Double, lang: String, city: String) {

        viewmodel.getWeather(lat, lon, lang, BuildConfig.ApiKey)


        lifecycleScope.launch {
            viewmodel.weather.collect {

                when (it) {
                    is ApiStatus.Success -> {
                        binding.nestedScrollView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        displayHourlyRecycleView(it.weatherResponse)
                        displayDailyRecycleView(it.weatherResponse)
                        displayWeatherInfo(it.weatherResponse, city)
                        //       binding.layoutFailedGetData.visibility = View.GONE
                        //-----Save Response in Room----------------------------------
                        //----I make this steps to put "id" with fixed number save one row in table
                        // ----and to Save City Name ------------------------------------
                        var obj = it.weatherResponse
                        var emptyList: List<Minutely> = emptyList()
                        var weatherDBModel = WeatherDBModel(
                            1,
                            city,
                            obj.current,
                            obj.daily,
                            obj.hourly,
                            obj.lat,
                            obj.lon,
                            emptyList,
                            obj.timezone,
                            obj.timezone_offset
                        )
                        viewmodelDatabase.insertWeatherInRoom(weatherDBModel)
                        //---------------------------------------


                    }
                    is ApiStatus.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.nestedScrollView.visibility = View.GONE
                        //    binding.layoutFailedGetData.visibility = View.GONE
                    }
                    is ApiStatus.Failed -> {
                        /* binding.layoutFailedGetData.visibility = View.VISIBLE
                          binding.progressBar.visibility = View.GONE
                          binding.nestedScrollView.visibility = View.GONE*/
                        binding.nestedScrollView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        viewmodelDatabase.getAllWeatherFromRoom()
                        viewmodelDatabase.allWeatherFromRoom.collect() { db ->
                            if (db != null) {
                                var weatherResponse = WeatherResponse(
                                    db[0].current,
                                    db[0].daily,
                                    db[0].hourly,
                                    db[0].lat,
                                    db[0].lon,
                                    db[0].minutely!!,
                                    db[0].timezone,
                                    db[0].timezone_offset
                                )
                                displayHourlyRecycleView(weatherResponse)
                                displayDailyRecycleView(weatherResponse)
                                displayWeatherInfo(weatherResponse, db[0].city)
                            }

                        }

                    }

                    else -> {}
                }

            }
        }
    }

    fun displayHourlyRecycleView(it: WeatherResponse) {
        var hourlyAdapter = HourAdapter(it,tempUnit)
        binding.rvHour.apply {
            adapter = hourlyAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1).apply {
                orientation = RecyclerView.HORIZONTAL
            }
        }
    }

    fun displayDailyRecycleView(it: WeatherResponse) {
        var dailyAdapter = DayAdapter(it,tempUnit)
        binding.rvDay.apply {
            adapter = dailyAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }

    fun displayWeatherInfo(wr: WeatherResponse, city: String) {
        binding.tvCity.text = city
        binding.tvDate.text = getDateTime(wr.current.dt)
        IconsApp.getSuitableIcon(wr.current.weather[0].icon, binding.imgStatusWeather)
        binding.tvCurrentTemp.text = ConvertUnits.convertTemp(wr.current.temp, tempUnit = tempUnit)

        binding.tvDescription.text = wr.current.weather.get(0).description
        //---------------------------------------------------------------------------------------

        binding.tvPressure.text = wr.current.pressure.toString() + " hpa"
        binding.tvHumidity.text = wr.current.humidity.toString() + " %"
        binding.tvWind.text = wr.current.wind_speed.toString() + " m/s"

        binding.tvCloud.text = wr.current.clouds.toString() + " %"
        binding.tvUvi.text = wr.current.uvi.toString()
        binding.tvVisibility.text = wr.current.visibility.toString() + " m"

        binding.tvTemperature.text = ConvertUnits.convertTemp(wr.current.temp, tempUnit = tempUnit)


        binding.tvSunRise.text = getDateTime(wr.current.sunrise, SimpleDateFormat("hh:mm aa"))

        binding.tvSunSet.text = getDateTime(wr.current.sunset, SimpleDateFormat("hh:mm aa"))

    }

    fun checkLanguage(language: String) {

        val locale = Locale(language)

        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(config, this.resources.displayMetrics)
    }


}
