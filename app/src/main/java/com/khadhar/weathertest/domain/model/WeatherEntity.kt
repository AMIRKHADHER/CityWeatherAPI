package com.khadhar.weathertest.domain.model

data class WeatherEntity(
    val tempMax: Double?,
    val tempMin: Double?,
    val name: String?,
    val description: String?,
    val icon: String?
)