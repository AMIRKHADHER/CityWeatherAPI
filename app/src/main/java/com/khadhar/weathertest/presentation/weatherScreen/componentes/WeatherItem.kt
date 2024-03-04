package com.khadhar.weathertest.presentation.weatherScreen.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.khadhar.weathertest.presentation.weatherScreen.WeatherEvent
import com.khadhar.weathertest.ui.theme.PeriwinkleBlue
import com.khadhar.weathertest.utils.WeatherCityStatus
import com.khadhar.weathertest.utils.toWeatherIconUrl

@Composable
fun WeatherItem(
    weatherResponse: WeatherCityStatus,
    modifier: Modifier = Modifier,
    onEvent: (WeatherEvent) -> Unit,
) {
    when (weatherResponse) {
        is WeatherCityStatus.Success -> {
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weatherResponse.data.icon!!.toString().toWeatherIconUrl())
                    .size(Size.ORIGINAL)
                    .build()
            ).state
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Row(
                    modifier = modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weatherResponse.data.name?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        weatherResponse.data.tempMin?.let {
                            Text(
                                text = "${it.toInt()}°",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = PeriwinkleBlue,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(36.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(14.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.Gray.copy(alpha = 0.7f))
                        )
                        weatherResponse.data.tempMax?.let {
                            Text(
                                text = "${it.toInt()}°",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.End,
                                color = PeriwinkleBlue,
                                modifier = Modifier
                                    .width(32.dp)
                                    .padding(end = 4.dp)
                            )
                        }
                        if (imageState is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .height(250.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(50.dp),
                                    imageVector = Icons.Rounded.ImageNotSupported,
                                    contentDescription = ""
                                )
                            }
                        }
                        if (imageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp),
                                painter = imageState.painter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        is WeatherCityStatus.Error -> {
            ErrorItem(
                errorMessage ="Erreur de Chargement de la Météo pour ${weatherResponse.message}" ,
                onRetryClicked = {
                    onEvent(WeatherEvent.GetWeatherByCity(weatherResponse.message))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        is WeatherCityStatus.Loading -> {
        }
        else -> {}
    }
}

@Composable
fun ErrorItem(
    errorMessage: String,
    onRetryClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                text = errorMessage,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                onClick = {
                    onRetryClicked()
                }
            ) {
                Text("Retry")
            }
        }
    }
}

