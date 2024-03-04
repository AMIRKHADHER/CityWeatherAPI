package com.khadhar.weathertest.presentation.weatherScreen

import com.khadhar.weathertest.utils.WeatherCityStatus


data class WeatherUiState(
    val isLoading: Boolean = false,
    var weatherResponseList: List<WeatherCityStatus> = emptyList(),
)
