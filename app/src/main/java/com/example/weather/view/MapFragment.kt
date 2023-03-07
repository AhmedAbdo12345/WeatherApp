package com.example.weather.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.data.database.FavouriteModel
import com.example.weather.R
import com.example.weather.viewmodel.FavouriteViewModel
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

//@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
lateinit var map:GoogleMap
lateinit var favouriteModel: FavouriteModel
    val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    private val favouriteViewModel: FavouriteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragement) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Create a new PlacesClient instance

        if (!Places.isInitialized()){
            Places.initialize(requireContext(), "AIzaSyAbuHdF1hB_ddtCtgEZ7iE3cDqXk4zpVJU")
        }
      //  val placesClient = Places.createClient(requireActivity())
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG))
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                place.latLng?.let {
                    map.addMarker(MarkerOptions().position(it).title(place.name))
                    /*
                    * zoom Levels
                    * 1)  world
                    * 5) landmoss /continent
                    * 10) city
                    * 15) streets
                    * 20) Buildings
                    * */
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(it,10f))
                }
                favouriteModel=FavouriteModel(place.name,place.latLng.latitude,place.latLng.longitude)

                Log.i("zxcv", "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("zxcv", "An error occurred: $status")
            }
        })


    }

    fun displayAlertDialog(model:FavouriteModel){
        if(model != null){
            var alert:AlertDialog.Builder=AlertDialog.Builder(requireContext())
            alert.setTitle("Save in Favourite")
            alert.setMessage("Do you want to save ${model.city} in Favourite")
            alert.setPositiveButton("Save"){ _: DialogInterface, _: Int ->

                favouriteViewModel.insertFavourite(model)
                Toast.makeText(requireContext(), "This location Saved Successfull", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("Cancle"){ _: DialogInterface, _: Int ->
            }

            val dialog=alert.create()

            dialog.show()
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        map.setOnMapLongClickListener { latlong->
            displayAlertDialog(favouriteModel)
        }
    }
}