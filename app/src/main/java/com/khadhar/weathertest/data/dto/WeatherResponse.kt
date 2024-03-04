package com.khadhar.weathertest.data.dto

import com.khadhar.weathertest.domain.model.WeatherEntity
import kotlin.math.round

data class WeatherResponse(
    val cod: Int,
    val id: Int?,
    val main: Main?,
    val name: String,
    val weather: List<Weather>?,
)

fun WeatherResponse.toWeatherEntity() = WeatherEntity(
    tempMax = main?.temp_max?.let { round(it) },
    tempMin = main?.let { round(it.temp_min) },
    name = name,
    description = weather?.get(0)?.description,
    icon = weather?.get(0)?.icon,
)