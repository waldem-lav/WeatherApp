package com.waldemlav.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.databinding.ListCityBinding
import com.waldemlav.weatherapp.data.cache.model.SavedCity

class CityListAdapter(
    private val onItemClicked: (SavedCity) -> Unit,
    private val onDeleteBtnPressed: (SavedCity) -> Unit,
) : ListAdapter<SavedCity, CityListAdapter.CityViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ListCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
            if (!holder.isCityCurrent())
                onDeleteBtnPressed(current)
        }
        holder.itemView.setOnClickListener {
            if (!holder.isCityCurrent())
                onItemClicked(current)
        }
    }

    class CityViewHolder(private var binding: ListCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(savedCity: SavedCity) {
            if (savedCity.isCurrent) {
                binding.ivIsCurrent.visibility = View.VISIBLE
                binding.ivDelete.visibility = View.GONE
            } else {
                binding.ivIsCurrent.visibility = View.GONE
                binding.ivDelete.visibility = View.VISIBLE
            }
            if (savedCity.aqi == null || savedCity.minTemp == null || savedCity.maxTemp == null)
                binding.tvAqiRv.text = "--/--"
            else {
                binding.tvAqiRv.text = "AQI ${savedCity.aqi}"
                binding.tvCityFragmentMaxMinTemp.text =
                    "${savedCity.maxTemp!!.toInt()}\u00B0/${savedCity.minTemp!!.toInt()}\u00B0"
            }
            if (savedCity.currentTemp == null)
                binding.tvCityFragmentTemp.text = "--"
            else
                binding.tvCityFragmentTemp.text = "${savedCity.currentTemp!!.toInt()}\u00B0"
            if (savedCity.state != "")
                binding.tvCityInfo.text =
                    "${savedCity.name}, ${savedCity.state} ,${savedCity.country}"
            else
                binding.tvCityInfo.text = "${savedCity.name}, ${savedCity.country}"
        }

        fun isCityCurrent(): Boolean = binding.ivIsCurrent.visibility == View.VISIBLE
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SavedCity>() {

            override fun areItemsTheSame(oldItem: SavedCity, newItem: SavedCity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SavedCity, newItem: SavedCity): Boolean {
                return oldItem.isCurrent == newItem.isCurrent && oldItem.name == newItem.name
                        && oldItem.country == newItem.country && oldItem.state == newItem.state
                        && oldItem.lon == newItem.lon && oldItem.lat == newItem.lat
            }
        }
    }
}