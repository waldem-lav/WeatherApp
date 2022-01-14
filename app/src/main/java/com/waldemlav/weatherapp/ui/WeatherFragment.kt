package com.waldemlav.weatherapp.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.databinding.FragmentWeatherBinding
import com.waldemlav.weatherapp.ui.adapters.ForecastListAdapter
import com.waldemlav.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect { uiState ->
                    if (uiState.city == null) {
                        getApi()
                        sharedViewModel.getCurrentCity()
                    } else {
                        binding.tvCity.text = uiState.city.name
                        if (uiState.shouldBeUpdated) {
                            sharedViewModel.shouldBeUpdated(false)
                            sharedViewModel.fetchWeather()
                        }
                        if (uiState.shouldSavedCityListBeUpdated) {
                            sharedViewModel.shouldSavedCityListBeUpdated(false)
                            sharedViewModel.updateAllSavedCities()
                        }
                    }
                    if (uiState.weather == null) {
                        sharedViewModel.setWeather()
                    } else {
                        if (uiState.weather.currentTemp[0] == '-') {
                            binding.tvMinus.text = "-"
                            binding.tvTemp.text = uiState.weather.currentTemp.drop(1)
                        } else
                            binding.tvTemp.text = uiState.weather.currentTemp

                        binding.tvWeatherDescription.text = uiState.weather
                            .weatherDescription.replaceFirstChar { it.uppercaseChar() }
                        binding.rvForecast.adapter = ForecastListAdapter(
                            uiState.weather.daily.slice(0..2)
                        )
                        binding.tvAqi.text = "AQI - ${uiState.weather.aqi.main}"
                    }
                    if (uiState.fetchingError != null) {
                        Toast.makeText(context, uiState.fetchingError, Toast.LENGTH_SHORT).show()
                        sharedViewModel.setFetchingErrorToNull()
                    }
                }
            }
        }

        binding.apply {
            weatherToolbar.inflateMenu(R.menu.main_menu)
            weatherToolbar.setNavigationIcon(R.drawable.ic_add_white)
            weatherToolbar.setNavigationOnClickListener {
                goToCityScreen()
            }
            btnForecast.setOnClickListener {
                goToForecastScreen()
            }
            tvDetails.setOnClickListener {
                goToForecastScreen()
            }
            cvAqi.setOnClickListener {
                findNavController().navigate(R.id.action_weatherFragment_to_airPollutionFragment)
            }
            binding.weatherToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_refresh -> {
                        sharedViewModel.fetchWeather()
                        sharedViewModel.updateAllSavedCities()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getApi() {
        requireContext().packageManager.getApplicationInfo(
            requireContext().packageName,
            PackageManager.GET_META_DATA
        ).apply {
            sharedViewModel.setApiKey(metaData.getString("com.waldemlav.weatherapp.API_KEY")!!)
        }
    }

    private fun goToCityScreen() {
        findNavController().navigate(R.id.action_weatherFragment_to_cityFragment)
    }

    private fun goToForecastScreen() {
        findNavController().navigate(R.id.action_weatherFragment_to_forecastFragment)
    }
}