package com.example.weather.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.data.utils.Constants
import com.example.weather.R
import com.example.weather.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
lateinit var binding:FragmentSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
   //     return inflater.inflate(R.layout.fragment_setting, container, false)

        binding=FragmentSettingBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        binding.cardViewLanguage.setOnClickListener(){
            if (binding.layoutLanguage.isVisible == true){
                binding.layoutLanguage.setVisibility(View.GONE)
            }else{
                binding.layoutLanguage.setVisibility(View.VISIBLE)
            }
        }

        binding.cardViewLocation.setOnClickListener(){
            if (binding.layoutLocation.isVisible == true){
                binding.layoutLocation.setVisibility(View.GONE)
            }else{
                binding.layoutLocation.setVisibility(View.VISIBLE)
            }
        }



        binding.radioGroupLanguage.checkedRadioButtonId

        binding.radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            val language =  if (R.id.appCompatRadioBtn_Arabic == checkedId) "ar" else "en"
                editor.putString(Constants.Language,language)
                editor.commit()

          NavHostFragment.findNavController(this@SettingFragment).navigate(R.id.action_settingFragment_to_mainActivity)




            //Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }

        binding.radioGroupLocation.setOnCheckedChangeListener { group, checkedId ->
            val location =  if (R.id.appCompatRadioBtn_GPS == checkedId) "GPS" else "Map"
            if (location.equals("Map")){
                editor.putString(Constants.Location,location)
                editor.commit()
                NavHostFragment.findNavController(this@SettingFragment).navigate(R.id.action_settingFragment_to_mapFragment)

            }


        }
    }
}