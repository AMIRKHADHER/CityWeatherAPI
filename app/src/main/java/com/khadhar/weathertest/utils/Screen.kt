package com.khadhar.weathertest.utils

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object WeatherPage : Screen("weather")
}