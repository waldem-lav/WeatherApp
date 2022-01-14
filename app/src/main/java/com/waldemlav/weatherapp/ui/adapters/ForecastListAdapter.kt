package com.waldemlav.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waldemlav.weatherapp.databinding.ListForecastBinding
import com.waldemlav.weatherapp.domain.model.Weather

class ForecastListAdapter(
    private val dataset: List<Weather.Daily>
) : RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ListForecastBinding.inflate(
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

    class ForecastViewHolder(private var binding: ListForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(dailyWeather: Weather.Daily) {
            binding.tvText.text = "${dailyWeather.dayOfWeek} \u2022 ${dailyWeather.mainDescription}"
            binding.tvTempRv.text = "${dailyWeather.tempMax}\u00B0/${dailyWeather.tempMin}\u00B0"
        }
    }

    override fun getItemCount() = dataset.size
}
