package com.example.weather.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.fav.FavouriteModel
import com.example.weather.R
import com.example.weather.databinding.FragmentFavBinding
import com.example.weather.view.adapter.FavAdapter
import com.example.weather.viewmodel.FavouriteViewModel
import kotlinx.coroutines.launch

class FavFragment : Fragment(),FavAdapter.ListItemClickListener,FavAdapter.ListItemClickListenerDelete {
    lateinit var binding: FragmentFavBinding
    var favAdapter = FavAdapter(this@FavFragment,this@FavFragment)

    private val viewmodel:FavouriteViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener(){
            NavHostFragment.findNavController(this@FavFragment)
                .navigate(R.id.action_favFragment_to_mapFragment)
        }

        viewmodel.getAllFavourite()
        lifecycleScope.launch {
            viewmodel.allFavourites.collect(){

                /* -this is old way -
                favAdapter = FavAdapter(it,this@FavFragment)

                binding.rvFavourite.apply {
                    adapter = favAdapter
                    setHasFixedSize(true)

                    layoutManager = GridLayoutManager(context, 1).apply {
                        orientation = RecyclerView.VERTICAL
                    }
                }*/

                favAdapter.submitList(it)
                binding.rvFavourite.apply {
                    adapter = favAdapter
                    setHasFixedSize(true)

                    layoutManager = GridLayoutManager(context, 1).apply {
                        orientation = RecyclerView.VERTICAL
                    }
                }
            }
        }
    }

    override fun onClickFav(favouriteModel: FavouriteModel) {

        val action: FavFragmentDirections.ActionFavFragmentToHomeFragment =
            FavFragmentDirections.actionFavFragmentToHomeFragment().setFavModel(favouriteModel)
        findNavController(view!!).navigate(action)
    }

    fun displayDeleteAlertDialog(model: FavouriteModel){
        var alert:AlertDialog.Builder=AlertDialog.Builder(requireContext())
        alert.setTitle(getString(R.string.Delete_Fav_Location))
        alert.setMessage(getString(R.string.Dialog_Delete_Fav_Message))
        alert.setPositiveButton(getString(R.string.Delete)){ _: DialogInterface, _: Int ->
            viewmodel.deleteFavourite(model)

            // --we using  notifyDataSetChanged in old way to update Adapter of recycleview ----
            //favAdapter.notifyDataSetChanged()
            NavHostFragment.findNavController(this@FavFragment).navigate(R.id.action_favFragment_self)


        }
        alert.setNegativeButton(getString(R.string.Cancle)){ _: DialogInterface, _: Int ->

        }
        var dialog=alert.create()
        dialog.show()
    }

    override fun onClickFavDelete(favouriteModel: FavouriteModel) {
        displayDeleteAlertDialog(favouriteModel)
    }
}