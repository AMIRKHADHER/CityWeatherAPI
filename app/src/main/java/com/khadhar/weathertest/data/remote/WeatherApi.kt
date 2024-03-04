package com.khadhar.weathertest.data.remote

import com.khadhar.weathertest.data.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("lang") lang: String = "fr",
        @Query("units") units:String="metric"
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "828f09be7e149a441fb0523a9d0df9f0"

    }

}