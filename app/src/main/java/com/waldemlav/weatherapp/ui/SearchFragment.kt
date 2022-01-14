package com.waldemlav.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.waldemlav.weatherapp.R
import com.waldemlav.weatherapp.data.cache.model.City
import com.waldemlav.weatherapp.data.cache.model.SavedCity
import com.waldemlav.weatherapp.databinding.FragmentSearchBinding
import com.waldemlav.weatherapp.ui.adapters.SearchListAdapter
import com.waldemlav.weatherapp.util.hideKeyboard
import com.waldemlav.weatherapp.ui.viewmodel.MainViewModel
import com.waldemlav.weatherapp.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

private const val MIN_QUERY_LENGTH = 3

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchListAdapter {
            sharedViewModel.insertSavedCity(mapToSavedCity(it))
            hideKeyboard()
        }

        binding.apply {
            tvCancel.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_cityFragment)
            }
            rvSearch.setHasFixedSize(true)
            rvSearch.adapter = adapter
            svCities.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null && query.length >= MIN_QUERY_LENGTH) {
                        searchCity(query)
                    }
                    return true
                }
            })
            svCities.setOnQueryTextFocusChangeListener { _, _ -> showKeyboard() }
            svCities.requestFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchCity(query: String) {
        sharedViewModel.getCities(query).observe((this.viewLifecycleOwner), { list ->
            adapter.submitList(list)
        })
    }

    private fun mapToSavedCity(city: City): SavedCity {
        return SavedCity(
            id = city.id, name = city.name, state = city.state, country = city.country,
            lon = city.lon, lat = city.lat, currentTemp = null, minTemp = null,
            maxTemp = null, aqi = null, isCurrent = false
        )
    }
}