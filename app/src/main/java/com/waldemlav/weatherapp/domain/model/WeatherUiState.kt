package com.waldemlav.weatherapp.domain.model

data class WeatherUiState(
    // Set true to update weather on app startup
    var shouldBeUpdated: Boolean = true,
    // Set true to update list of saved cities on app startup
    var shouldSavedCityListBeUpdated: Boolean = true,
    var fetchingError: String? = null,
    val city: CurrentCity? = null,
    val weather: Weather? = null
)