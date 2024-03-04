package com.khadhar.weathertest.presentation.weatherScreen


sealed interface WeatherEvent {
    data class GetWeatherByCity(val city: String) : WeatherEvent
    data object  ClearData : WeatherEvent
}