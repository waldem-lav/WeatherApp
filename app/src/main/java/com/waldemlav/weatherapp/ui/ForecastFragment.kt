package com.waldemlav.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.databinding.FragmentForecastBinding
import com.waldemlav.weatherapp.ui.adapters.FullForecastListAdapter
import com.waldemlav.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.uiState.collect { uiState ->
                    if (uiState.weather != null)
                        binding.rvForecastFull.adapter =
                            FullForecastListAdapter(uiState.weather.daily)
                }
            }
        }

        binding.apply {
            rvForecastFull.setHasFixedSize(true)
            forecastToolbar.setNavigationIcon(R.drawable.ic_back)
            forecastToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_forecastFragment_to_weatherFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}