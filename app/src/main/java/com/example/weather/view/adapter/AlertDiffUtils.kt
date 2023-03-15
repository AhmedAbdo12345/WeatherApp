package com.example.weather.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.data.database.alert.AlertModel
import com.example.data.database.fav.FavouriteModel

class AlertDiffUtils: DiffUtil.ItemCallback<AlertModel>()  {
    override fun areItemsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlertModel, newItem: AlertModel): Boolean {
        return oldItem == newItem
    }
}