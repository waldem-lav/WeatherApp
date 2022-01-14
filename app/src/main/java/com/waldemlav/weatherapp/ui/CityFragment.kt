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
import com.waldemlav.weatherapp.databinding.FragmentCityBinding
import com.waldemlav.weatherapp.ui.adapters.CityListAdapter
import com.waldemlav.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityFragment : Fragment() {

    private var _binding: FragmentCityBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: CityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CityListAdapter(
            onDeleteBtnPressed = {
                sharedViewModel.deleteSavedCity(it)
            },
            onItemClicked = {
                it.isCurrent = true
                sharedViewModel.setNewCurrentCity(it)
            }
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.getSavedCities().collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.apply {
            cityToolbar.setNavigationIcon(R.drawable.ic_back)
            cityToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_cityFragment_to_weatherFragment)
            }
            rvSavedCities.setHasFixedSize(true)
            rvSavedCities.adapter = adapter
            cvSearch.setOnClickListener {
                findNavController().navigate(R.id.action_cityFragment_to_searchFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}