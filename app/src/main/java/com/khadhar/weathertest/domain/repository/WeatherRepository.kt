package com.khadhar.weathertest.domain.repository

import com.khadhar.weathertest.domain.model.WeatherEntity

interface WeatherRepository {
    suspend fun getWeather(
        city:String
    ): Result<WeatherEntity>

}