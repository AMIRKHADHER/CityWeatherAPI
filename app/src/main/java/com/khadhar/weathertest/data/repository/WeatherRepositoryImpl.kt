package com.khadhar.weathertest.data.repository

import com.khadhar.weathertest.data.dto.toWeatherEntity
import com.khadhar.weathertest.data.remote.WeatherApi
import com.khadhar.weathertest.domain.model.WeatherEntity
import com.khadhar.weathertest.domain.repository.WeatherRepository
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(
        city: String
    ): Result<WeatherEntity> {
      return  try {
            Result.success(weatherApi.getWeather(city).toWeatherEntity())
        } catch (e: Exception) {
            Result.failure(Throwable(""))
        }
    }
}