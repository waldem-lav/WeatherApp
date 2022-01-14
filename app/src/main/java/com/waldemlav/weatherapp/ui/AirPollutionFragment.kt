package com.waldemlav.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.databinding.FragmentAirPollutionBinding
import com.waldemlav.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AirPollutionFragment : Fragment() {

    private var _binding: FragmentAirPollutionBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirPollutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect { uiState ->
                    if (uiState.weather != null) {
                        val date = Date(uiState.weather.aqi.dt * 1000L)
                        val formatter = SimpleDateFormat("a h:mm", Locale.getDefault())
                        binding.tvPublishedAt.text = "Published at ${formatter.format(date)}"

                        val color = when (uiState.weather.aqi.main) {
                            in 1..2 -> ContextCompat.getColor(requireContext(), R.color.green)
                            in 3..4 -> ContextCompat.getColor(requireContext(), R.color.yellow)
                            else -> ContextCompat.getColor(requireContext(), R.color.red)
                        }

                        binding.apply {
                            tvAqiValue.text = uiState.weather.aqi.main.toString()
                            tvAqiValue.setTextColor(color)
                            tvAqiText.text = when (uiState.weather.aqi.main) {
                                1 -> "Good"
                                2 -> "Fair"
                                3 -> "Moderate"
                                4 -> "Poor"
                                else -> "Very Poor"
                            }
                            tvAqiText.setTextColor(color)
                            tvPm25Value.text = uiState.weather.aqi.pm25
                            tvPm25Value.setTextColor(color)
                            tvPm10Value.text = uiState.weather.aqi.pm10
                            tvPm10Value.setTextColor(color)
                            tvSo2Value.text = uiState.weather.aqi.so2
                            tvSo2Value.setTextColor(color)
                            tvNo2Value.text = uiState.weather.aqi.no2
                            tvNo2Value.setTextColor(color)
                            tvO3Value.text = uiState.weather.aqi.o3
                            tvO3Value.setTextColor(color)
                            tvCoValue.text = uiState.weather.aqi.co
                            tvCoValue.setTextColor(color)
                        }
                    }
                }
            }
        }

        binding.apply {
            pollutionToolbar.setNavigationIcon(R.drawable.ic_back)
            pollutionToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_airPollutionFragment_to_weatherFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}