package com.example.weather.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.FavouriteModel
import com.example.weather.databinding.RvFavBinding
import kotlinx.coroutines.flow.MutableStateFlow

import kotlin.collections.List
class FavAdapter (var deleteClickListener: FavAdapter.ListItemClickListenerDelete,var mClickListener: FavAdapter.ListItemClickListener) : ListAdapter<FavouriteModel, FavAdapter.FavViewHolder>(FavDiffUtils()) {

    lateinit var binding: RvFavBinding
    interface ListItemClickListenerDelete {
        fun onClickFavDelete(favouriteModel: FavouriteModel)
    }
    interface ListItemClickListener {
        fun onClickFav(favouriteModel: FavouriteModel)
    }
    class FavViewHolder(var binding: RvFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        //    var inflate=(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))as LayoutInflater
        var inflate = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RvFavBinding.inflate(inflate, parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int)  {
        holder.binding.tvCityFav.text=getItem(position).city
        holder.binding.favModel= getItem(position)
        holder.binding.actionDelete= deleteClickListener
        holder.binding.action= mClickListener
    }
}

/*class FavAdapter (var listFavModel: List<FavouriteModel>?, var mClickListener: ListItemClickListener) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    lateinit var binding: RvFavBinding
    interface ListItemClickListener {
        fun onClickFav(favouriteModel: FavouriteModel)
    }
    class FavViewHolder(var binding: RvFavBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        //    var inflate=(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))as LayoutInflater
        var inflate = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RvFavBinding.inflate(inflate, parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int)  {
        if (listFavModel != null) {
            holder.binding.tvCityFav.text= listFavModel!![position].city
            holder.binding.favModel= listFavModel!![position]
            holder.binding.action=mClickListener
        }
    }
    override fun getItemCount(): Int {
        return listFavModel?.size ?: 2
    }
}*/
