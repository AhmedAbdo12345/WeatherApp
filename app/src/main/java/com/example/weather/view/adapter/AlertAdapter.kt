package com.example.weather.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.alert.AlertModel
import com.example.weather.databinding.RvAlertBinding

class AlertAdapter(var deleteClickListener: AlertAdapter.ListItemClickListenerDelete) : ListAdapter<AlertModel, AlertAdapter.AlertViewHolder>(AlertDiffUtils()) {

    lateinit var binding: RvAlertBinding

    interface ListItemClickListenerDelete {
        fun onClickAlertDelete(alertModel: AlertModel)
    }

    class AlertViewHolder(var binding: RvAlertBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        //    var inflate=(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))as LayoutInflater
        var inflate = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RvAlertBinding.inflate(inflate, parent, false)
        return AlertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int)  {
        holder.binding.tvTimeStart.text=getItem(position).timeStart
        holder.binding.tvDateStart.text= getItem(position).dateStart
        holder.binding.tvTimeEnd.text=getItem(position).timeEnd
        holder.binding.tvDateEnd.text= getItem(position).dateEnd
        holder.binding.alertModel= getItem(position)
        holder.binding.actionDelete= deleteClickListener
    }


}
