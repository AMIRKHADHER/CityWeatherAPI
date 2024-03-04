package com.khadhar.weathertest.domain.usecase

import com.khadhar.weathertest.domain.model.WeatherEntity
import com.khadhar.weathertest.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<WeatherEntity> {
        return weatherRepository.getWeather(city = city)
    }
}