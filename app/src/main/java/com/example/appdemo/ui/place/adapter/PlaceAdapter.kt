package com.example.appdemo.ui.place.adapter

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemo.databinding.ItemPlaceBinding
import com.example.core.MY_LOCATION
import com.example.core.data.PlaceData
import java.math.RoundingMode

class PlaceAdapter :
    PagingDataAdapter<PlaceData.PlaceDetail, PlaceAdapter.PlaceViewHolder>(PlaceComparator) {

    class PlaceViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    object PlaceComparator : DiffUtil.ItemCallback<PlaceData.PlaceDetail>() {
        override fun areItemsTheSame(
            oldItem: PlaceData.PlaceDetail,
            newItem: PlaceData.PlaceDetail
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: PlaceData.PlaceDetail,
            newItem: PlaceData.PlaceDetail
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        with(holder) {
            Glide.with(binding.placeImg.context)
                .load(getItem(position)!!.icon)
                .fitCenter()
                .into(binding.placeImg)

            binding.placeName.text = getItem(position)!!.name
            binding.placeAddress.text = getItem(position)!!.vicinity

            val start = Location("").apply {
                latitude = MY_LOCATION.first.toDouble()
                longitude = MY_LOCATION.second.toDouble()
            }
            val des = Location("").apply {
                latitude = getItem(position)!!.geometry!!.locationDetail!!.lat!!.toDouble()
                longitude = getItem(position)!!.geometry!!.locationDetail!!.lng!!.toDouble()
            }
            val distance = start.distanceTo(des)
            binding.placeDistance.text =
                "${(distance / 1000).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()} Km."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(
            ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}