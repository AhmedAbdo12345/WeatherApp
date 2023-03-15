package com.example.weather.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.data.database.fav.FavouriteModel
import com.example.data.utils.Constants
import com.example.weather.R
import com.example.weather.databinding.FragmentMapBinding
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
    lateinit var map: GoogleMap
    lateinit var favouriteModel: FavouriteModel
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
    var locationType: String = ""
    lateinit var binding:FragmentMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
     /*   binding= FragmentMapBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner=this
        return binding.root*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        favouriteModel=getDefaultLocationWhenOpenMapScreen()
        locationType = MapFragmentArgs.fromBundle(requireArguments()).location.toString()


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragement) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Create a new PlacesClient instance

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyAbuHdF1hB_ddtCtgEZ7iE3cDqXk4zpVJU")
        }
        //  val placesClient = Places.createClient(requireActivity())
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                //  Get info about the selected place.
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
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
                }
                favouriteModel =
                    FavouriteModel(place.name, place.latLng.latitude, place.latLng.longitude)

                Log.i("zxcv", "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("zxcv", "An error occurred: $status")
            }
        })


    }

    fun displayAlertDialogToSaveFavourite(model: FavouriteModel) {
        if (model != null) {
            var alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            alert.setTitle(getString(R.string.Save_In_Favourite))
            alert.setMessage(getString(R.string.Dialog_Save_Fav_Message))
            alert.setPositiveButton(getString(R.string.Save)) { _: DialogInterface, _: Int ->

                favouriteViewModel.insertFavourite(model)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.Save_Successfull),
                    Toast.LENGTH_SHORT
                ).show()
                NavHostFragment.findNavController(this@MapFragment).navigate(R.id.action_mapFragment_to_favFragment)
            }
            alert.setNegativeButton(getString(R.string.Cancle)) { _: DialogInterface, _: Int ->
            }

            val dialog = alert.create()

            dialog.show()
        }

    }

    fun displayAlertDialogToSaveCurrentLocation(model: FavouriteModel) {
        if (model != null) {
            var alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            alert.setTitle(getString(R.string.Save_Location))
            alert.setMessage(getString(R.string.Dialog_Save_Map_Message))
            alert.setPositiveButton(getString(R.string.Save)) { _: DialogInterface, _: Int ->
                editor.putString(Constants.Location, "Map")
                editor.putString(Constants.CityName, model.city)
                editor.putString(Constants.Latitude, (model.latitude).toString())
                editor.putString(Constants.Longitude, (model.longitude).toString())
                editor.commit()

                NavHostFragment.findNavController(this@MapFragment).navigate(R.id.action_mapFragment_to_homeFragment)

            }
            alert.setNegativeButton(getString(R.string.Cancle)) { _: DialogInterface, _: Int ->
            }

            val dialog = alert.create()

            dialog.show()
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val defaultLocation = LatLng(favouriteModel.latitude, favouriteModel.longitude)
        map.addMarker(MarkerOptions().position(defaultLocation).title(favouriteModel.city))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,10f))

        map.setOnMapLongClickListener { latlong ->

            if (locationType.equals("Map")) {
                displayAlertDialogToSaveCurrentLocation(favouriteModel)
            } else {
                displayAlertDialogToSaveFavourite(favouriteModel)
            }
        }
    }

    fun getDefaultLocationWhenOpenMapScreen(): FavouriteModel {
        val city = sharedPreference.getString(Constants.CityName, "")
        val lat = sharedPreference.getString(Constants.Latitude, "0.0")
        val lng = sharedPreference.getString(Constants.Longitude, "0.0")
        favouriteModel = FavouriteModel(city!!, lat?.toDouble() ?: 0.0, lng?.toDouble() ?: 0.0)
        return favouriteModel
    }
}