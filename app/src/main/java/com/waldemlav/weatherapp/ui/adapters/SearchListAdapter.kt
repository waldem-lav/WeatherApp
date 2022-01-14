package com.waldemlav.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.data.cache.model.City
import com.waldemlav.weatherapp.databinding.ListSearchBinding

class SearchListAdapter(private val onAddBtnPressed: (City) -> Unit) :
    ListAdapter<City, SearchListAdapter.SearchViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ListSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.findViewById<ImageView>(R.id.iv_add).setOnClickListener {
            onAddBtnPressed(current)
        }
    }

    class SearchViewHolder(private var binding: ListSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(city: City) {
            binding.tvCity.text = city.name
            if (city.state != "")
                binding.tvCountry.text = "${city.state}, ${city.country}"
            else
                binding.tvCountry.text = city.country
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<City>() {

            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: City, newItem: City
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
                        && oldItem.state == newItem.state && oldItem.lon == newItem.lon
                        && oldItem.lat == newItem.lat
            }
        }
    }
}