package com.waldemlav.weatherapp.ui.viewmodel

import androidx.lifecycle.*
import com.waldemlav.weatherapp.data.repository.MainRepository
import com.waldemlav.weatherapp.data.cache.model.City
import com.waldemlav.weatherapp.data.cache.model.SavedCity
import com.waldemlav.weatherapp.domain.model.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val MAX_SAVED_CITIES = 10

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private lateinit var apiKey: String

    fun setApiKey(key: String) {
        apiKey = key
    }

    private lateinit var coord: Pair<String, String> // lon, lat

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun shouldBeUpdated(boolean: Boolean) = _uiState.update { it.copy(shouldBeUpdated = boolean) }

    fun shouldSavedCityListBeUpdated(boolean: Boolean) {
        _uiState.update { it.copy(shouldSavedCityListBeUpdated = boolean) }
    }

    fun getCurrentCity() {
        viewModelScope.launch {
            val currentCity = repository.getCurrentSavedCity()
            coord = Pair(currentCity!!.lon, currentCity.lat)
            _uiState.update { it.copy(city = currentCity) }
        }
    }

    fun setNewCurrentCity(savedCity: SavedCity) {
        viewModelScope.launch {
            repository.resetCurrentSavedCity()
            repository.deleteCachedData()
            repository.updateSavedCity(savedCity)
            getCurrentCity()
            _uiState.update { it.copy(weather = null) }
            _uiState.update { it.copy(shouldBeUpdated = true) }
        }
    }

    fun setWeather() {
        viewModelScope.launch {
            val weather = repository.getWeather()
            if (weather != null)
                _uiState.update { it.copy(weather = weather) }
        }
    }

    private var fetchJob: Job? = null
    fun fetchWeather() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val weather =
                repository.fetchWeather(lon = coord.first, lat = coord.second, apiKey = apiKey)
            val pollution =
                repository.fetchAirPollution(lon = coord.first, lat = coord.second, apiKey = apiKey)
            if (weather.data != null && pollution.data != null) {
                repository.cacheWeather(weather.data, pollution.data)
                setWeather()
            } else {
                if (weather.message != "StandaloneCoroutine was cancelled")
                    _uiState.update { it.copy(fetchingError = weather.message) }
            }
        }
    }

    fun setFetchingErrorToNull() = _uiState.update { it.copy(fetchingError = null) }

    fun getCities(cityName: String): LiveData<List<City>> {
        return repository.getCitiesByName(cityName).asLiveData()
    }

    fun getSavedCities(): Flow<List<SavedCity>> {
        return repository.getSavedCitiesAsFlow()
    }

    private var fetchListJob: Job? = null
    fun updateAllSavedCities() {
        fetchListJob?.cancel()
        fetchListJob = viewModelScope.launch {
            repository.getSavedCities().forEach {
                repository.updateSavedCityWeatherData(it, apiKey)
            }
        }
    }

    fun deleteSavedCity(savedCity: SavedCity) {
        viewModelScope.launch {
            repository.deleteSavedCity(savedCity)
        }
    }

    fun insertSavedCity(savedCity: SavedCity) {
        viewModelScope.launch {
            when (repository.getSavedCitiesCount()) {
                in 0 until MAX_SAVED_CITIES -> {
                    if (!repository.isCityAlreadySaved(savedCity.id))
                        repository.insertSavedCity(savedCity)
                }
            }
        }
    }
}