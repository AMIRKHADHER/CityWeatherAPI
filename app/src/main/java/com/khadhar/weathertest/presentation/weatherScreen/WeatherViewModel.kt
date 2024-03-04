package com.khadhar.weathertest.presentation.weatherScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khadhar.weathertest.domain.usecase.GetWeatherByCityUseCase
import com.khadhar.weathertest.utils.WeatherCityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase
) : ViewModel() {

    private var _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState = _weatherUiState.asStateFlow()

    private fun clearData() {
        _weatherUiState.update {
            it.copy(weatherResponseList = emptyList())
        }
    }
    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.GetWeatherByCity -> getWeather(event.city)
            is WeatherEvent.ClearData -> clearData()
            else -> {}
        }
    }
    private fun getWeather(city: String) {
        viewModelScope.launch {
            _weatherUiState.update {
                it.copy(isLoading = true)
            }
            getWeatherByCityUseCase(
                city
            ).onSuccess { weatherEntity ->
                val ifWeatherAlreadyExist = _weatherUiState.value.weatherResponseList.contains(
                    WeatherCityStatus.Error(message = city)
                )
                if (ifWeatherAlreadyExist) {
                    _weatherUiState.update { it ->
                        it.copy(
                            weatherResponseList = weatherUiState.value.weatherResponseList.filter {
                                it != WeatherCityStatus.Error(message = city)
                            } + WeatherCityStatus.Success(weatherEntity),
                            isLoading = false
                        )
                    }
                } else {
                    _weatherUiState.update {
                        it.copy(
                            weatherResponseList = weatherUiState.value.weatherResponseList + WeatherCityStatus.Success(
                                weatherEntity
                            ),
                            isLoading = false
                        )
                    }
                }
            }.onFailure {
                val ifWeatherAlreadyExist = _weatherUiState.value.weatherResponseList.contains(
                    WeatherCityStatus.Error(message = city)
                )
                if (!ifWeatherAlreadyExist) {
                    _weatherUiState.update {
                        it.copy(
                            weatherResponseList = weatherUiState.value.weatherResponseList
                                    + WeatherCityStatus.Error(city),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}
























