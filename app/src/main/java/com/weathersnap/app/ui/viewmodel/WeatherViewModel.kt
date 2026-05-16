package com.weathersnap.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathersnap.app.data.remote.OpenMeteoApi
import com.weathersnap.app.data.remote.models.CityDto
import com.weathersnap.app.data.remote.models.CurrentWeatherDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeatherUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val cityName: String? = null,
    val weather: CurrentWeatherDto? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: OpenMeteoApi
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _suggestions = MutableStateFlow<List<CityDto>>(emptyList())
    val suggestions: StateFlow<List<CityDto>> = _suggestions.asStateFlow()

    private val _weatherState = MutableStateFlow(WeatherUiState())
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    // Cache for city suggestions
    private val suggestionCache = mutableMapOf<String, List<CityDto>>()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.length > 2) {
            searchCities(query)
        } else {
            _suggestions.value = emptyList()
        }
    }

    private fun searchCities(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // debounce
            
            // Check cache
            val cached = suggestionCache[query.lowercase()]
            if (cached != null) {
                _suggestions.value = cached
                return@launch
            }

            try {
                val response = api.searchCity(name = query)
                val results = response.results ?: emptyList()
                suggestionCache[query.lowercase()] = results
                _suggestions.value = results
            } catch (e: Exception) {
                e.printStackTrace()
                _suggestions.value = emptyList()
            }
        }
    }

    fun selectCity(city: CityDto) {
        _searchQuery.value = city.name
        _suggestions.value = emptyList()
        fetchWeather(city)
    }

    private fun fetchWeather(city: CityDto) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState(isLoading = true)
            try {
                val response = api.getCurrentWeather(
                    latitude = city.latitude,
                    longitude = city.longitude
                )
                if (response.current != null) {
                    _weatherState.value = WeatherUiState(
                        isLoading = false,
                        cityName = "${city.name}, ${city.country ?: ""}".trimEnd(',', ' '),
                        weather = response.current
                    )
                } else {
                    _weatherState.value = WeatherUiState(error = "No weather data available")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherState.value = WeatherUiState(error = "Failed to fetch weather: ${e.message}")
            }
        }
    }
    
    fun mapWeatherCode(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1, 2, 3 -> "Partly cloudy"
            45, 48 -> "Fog"
            51, 53, 55, 56, 57 -> "Drizzle"
            61, 63, 65, 66, 67 -> "Rain"
            71, 73, 75, 77 -> "Snow"
            80, 81, 82 -> "Rain showers"
            85, 86 -> "Snow showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown ($code)"
        }
    }
}
