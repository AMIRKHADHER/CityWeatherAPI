package com.khadhar.weathertest.utils

import com.khadhar.weathertest.domain.model.WeatherEntity

sealed class WeatherCityStatus {
    data class Success(val data: WeatherEntity) : WeatherCityStatus()
    data class Error(val message: String) : WeatherCityStatus()
    data object Loading : WeatherCityStatus()
}