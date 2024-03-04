package com.khadhar.weathertest.presentation.weatherScreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.khadhar.weathertest.R
import com.khadhar.weathertest.presentation.weatherScreen.componentes.CustomButton
import com.khadhar.weathertest.presentation.weatherScreen.componentes.Header
import com.khadhar.weathertest.presentation.weatherScreen.componentes.WeatherItem
import com.khadhar.weathertest.ui.theme.Gray
import com.khadhar.weathertest.ui.theme.PeriwinkleBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun WeatherScreen(
    navController: NavHostController,
    goBack: () -> Unit,
    onEvent: (WeatherEvent) -> Unit,
    state: WeatherUiState,
) {
    // val viewModel = hiltViewModel<WeatherViewModel>()
    // val state = viewModel.weatherState.collectAsState().value
    var isRunning by remember { mutableStateOf(true) }

    Scaffold(


    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Header(
                 stringResource(id = R.string.weather_multiville) ,
                goBack = goBack
            )
            var elapsedTime by remember { mutableIntStateOf(0) }
            var currentText by remember { mutableStateOf("") }
            val textList = listOf(
                "Nous téléchargeons les données…",
                " C’est presque fini…",
                "Plus que quelques secondes avant d’avoir le résultat…"
            )
            val cities = remember { listOf("Rennes", "Paris", "Nantes", "Bordeaux", "Lyon") }
            var currentIndex by remember { mutableIntStateOf(0) }
            var currentProgress by remember { mutableFloatStateOf(0f) }
            LaunchedEffect(key1 = isRunning) {
                while (elapsedTime < 60) {
                    delay(1000)
                    elapsedTime++
                }
            }
            LaunchedEffect(key1 = isRunning) {
                while (elapsedTime < 60) { // Change every 6 seconds for 60 seconds
                    currentText = textList[currentIndex]
                    delay(6000) // 6 seconds
                    currentIndex = (currentIndex + 1) % textList.size
                }
            }
            LaunchedEffect(key1 = true) {
                cities.forEachIndexed { _, city ->
                    onEvent(WeatherEvent.GetWeatherByCity(city))
                    delay(10000) // Attend 10 secondes
                }
            }
            if (elapsedTime == 60) LazyColumn(
                modifier = Modifier.align(Alignment.Center)
            ) {
                items(state.weatherResponseList.size) { i ->
                    val weatherItem = state.weatherResponseList[i]
                    WeatherItem(weatherResponse = weatherItem, onEvent = onEvent)
                }
            }
            val scope = rememberCoroutineScope()

            if (elapsedTime == 60) CustomButton(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
                text = stringResource(R.string.start_again),
                onClick = {
                    onEvent(WeatherEvent.ClearData)
                    scope.launch {
                        currentProgress = 0f
                        cities.forEachIndexed { _, city ->
                            onEvent(WeatherEvent.GetWeatherByCity(city))
                            delay(10000)
                        }
                    }
                    isRunning = !isRunning
                    elapsedTime = 0
                })
            else
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 12.dp),

                    ) {
                    val animatedProgress1 by animateFloatAsState(
                        targetValue = elapsedTime.toFloat() / 10,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                        label = ""
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = currentText, fontSize = 20.sp,
                        color = PeriwinkleBlue,
                        fontWeight = FontWeight.Medium,
                    )
                    LinearProgressIndicator(progress = {
                        elapsedTime.toFloat()
                    })
                }
        }
    }
}

@Composable
fun LinearProgressIndicator(
    progress: () -> Float,
    color: Color = PeriwinkleBlue,
) {
    val animatedProgress1 by animateFloatAsState(
        targetValue = progress() / 100f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Gray,
                shape = shape,
            )
            .clip(shape),
    ) {
        LinearProgressIndicator(
            progress = animatedProgress1, modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(), color = PeriwinkleBlue
        )
        Text(
            text = "${(progress() * 100 / 60).toInt()} %",
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 50.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
    )
}

private fun getImageUrl(iconCode: String?): String =
    StringBuilder("https://openweathermap.org/img/wn/")
        .append(iconCode)
        .append("@4x.png")
        .toString()