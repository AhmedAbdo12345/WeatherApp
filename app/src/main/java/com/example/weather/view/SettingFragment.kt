package com.example.weather.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.data.utils.Constants
import com.example.weather.R
import com.example.weather.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding

    var tempUnit=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //     return inflater.inflate(R.layout.fragment_setting, container, false)

        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        //--------------------------------------------------------------------------------------
       var lastLocationType= sharedPreference.getString(Constants.Location,"GPS")
        if (lastLocationType.equals("GPS")){
            binding.radioGroupLocation.check(binding.appCompatRadioBtnGPS.id)
        }else{
            binding.radioGroupLocation.check(binding.appCompatRadioBtnMap.id)
        }
        //--------------------------------------------------------------------------------------
        var lastLanguageType= sharedPreference.getString(Constants.Language,"en")
        if (lastLanguageType.equals("en")){
            binding.radioGroupLanguage.check(binding.appCompatRadioBtnEnglish.id)
        }else{
            binding.radioGroupLanguage.check(binding.appCompatRadioBtnArabic.id)
        }
        //--------------------------------------------------------------------------------------
         tempUnit= sharedPreference.getString(Constants.Temperature,Constants.Celsius).toString()
        Log.i("zxcv", "temp: $tempUnit")
        when (tempUnit) {
            Constants.Celsius -> binding.radioGroupTemperature.check(binding.appCompatRadioBtnCelsius.id)
            Constants.Kelvin -> binding.radioGroupTemperature.check(binding.appCompatRadioBtnKelvin.id)
           Constants.Fahrenheit -> binding.radioGroupTemperature.check(binding.appCompatRadioBtnFahrenheit.id)
        else -> { }
        }
        //--------------------------------------------------------------------------------------

        binding.layoutLanguageTitle.setOnClickListener() {
            if (binding.layoutLanguage.isVisible == true) {
                binding.layoutLanguage.setVisibility(View.GONE)
            } else {
                binding.layoutLanguage.setVisibility(View.VISIBLE)
            }
        }

        binding.layoutLocationTitle.setOnClickListener() {
            if (binding.layoutLocation.isVisible == true) {
                binding.layoutLocation.setVisibility(View.GONE)
            } else {
                binding.layoutLocation.setVisibility(View.VISIBLE)
            }
        }

        binding.layoutTemperatureTitle.setOnClickListener() {
            if (binding.layoutTemperature.isVisible == true) {
                binding.layoutTemperature.setVisibility(View.GONE)
            } else {
                binding.layoutTemperature.setVisibility(View.VISIBLE)
            }
        }

        binding.radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            val language = if (R.id.appCompatRadioBtn_Arabic == checkedId) "ar" else "en"
            editor.putString(Constants.Language, language)
            editor.commit()

            NavHostFragment.findNavController(this@SettingFragment)
                .navigate(R.id.action_settingFragment_to_mainActivity)

        }

        binding.appCompatRadioBtnMap.setOnClickListener {
               val action: SettingFragmentDirections.ActionSettingFragmentToMapFragment =
                 SettingFragmentDirections.actionSettingFragmentToMapFragment().setLocation("Map")
             Navigation.findNavController(view).navigate(action)
        }
        binding.appCompatRadioBtnGPS.setOnClickListener {
            editor.putString(Constants.Location, "GPS")
            editor.commit()
            NavHostFragment.findNavController(this@SettingFragment)
                .navigate(R.id.action_settingFragment_to_homeFragment)
        }

        binding.radioGroupTemperature.setOnCheckedChangeListener { group, checkedId ->

            when(checkedId){
                R.id.appCompatRadioBtn_Celsius -> tempUnit=Constants.Celsius
                R.id.appCompatRadioBtn_Kelvin -> tempUnit=Constants.Kelvin
                R.id.appCompatRadioBtn_Fahrenheit -> tempUnit=Constants.Fahrenheit
                else -> {

                }
            }
           editor.putString(Constants.Temperature, tempUnit)
            editor.commit()

           NavHostFragment.findNavController(this@SettingFragment)
                .navigate(R.id.action_settingFragment_to_homeFragment)
        }
    }
}