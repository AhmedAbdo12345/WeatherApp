package com.example.weather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.LocationServices
import java.util.*


const val permissionId = 19

class CurrentLocation(
    val context: Context,
    val activity: Activity,
    val currentLocationStatue: CurrentLocationStatue,
    val language:String
) {
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }



    private fun requestPermissions() {
      requestPermissions(
            activity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), permissionId
        )
        getLocation()

    }



    @SuppressLint("MissingPermission")
    fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                LocationServices.getFusedLocationProviderClient(activity).lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(context, Locale(language))
                            var list = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                            currentLocationStatue.success(list)
                            Log.i("zxcv", "getLocation3555: ${list}")
                        }catch (e :Exception){
                            Log.i("zxcv", "getLocation: "+e.message)
                        }

                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
             } else {
                 requestPermissions()


        }
    }

}