

package com.khadhar.weathertest.utils


 fun String.toWeatherIconUrl(): String =
    StringBuilder("https://openweathermap.org/img/wn/")
        .append(this)
        .append("@4x.png")
        .toString()
