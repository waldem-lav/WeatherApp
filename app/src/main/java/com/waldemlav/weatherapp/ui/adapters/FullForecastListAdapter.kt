package com.waldemlav.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waldemlav.weatherapp.databinding.ListFullForecastBinding
import com.waldemlav.weatherapp.domain.model.Weather

class FullForecastListAdapter(
    private val dataset: List<Weather.Daily>
) : RecyclerView.Adapter<FullForecastListAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ListFullForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val current = dataset[position]
        holder.bind(current)
    }

    class ForecastViewHolder(private var binding: ListFullForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(dailyWeather: Weather.Daily) {
            binding.tvInfo.text = "${dailyWeather.dayOfWeek} • ${dailyWeather.mainDescription}"
            binding.tvTempRv.text = "${dailyWeather.tempMax}\u00B0/${dailyWeather.tempMin}\u00B0"
            binding.tvDate.text = dailyWeather.date
        }
    }

    override fun getItemCount() = dataset.size
}