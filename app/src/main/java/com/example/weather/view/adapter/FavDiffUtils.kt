package com.example.weather.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.data.database.FavouriteModel

class FavDiffUtils: DiffUtil.ItemCallback<FavouriteModel>() {
    override fun areItemsTheSame(oldItem: FavouriteModel, newItem: FavouriteModel): Boolean {
        return oldItem.city == newItem.city
    }

    override fun areContentsTheSame(oldItem: FavouriteModel, newItem: FavouriteModel): Boolean {
return oldItem == newItem
    }

}