package com.khadhar.weathertest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khadhar.weathertest.presentation.home.HomeScreen
import com.khadhar.weathertest.presentation.weatherScreen.WeatherScreen
import com.khadhar.weathertest.presentation.weatherScreen.WeatherViewModel
import com.khadhar.weathertest.utils.Screen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.rout
    ) {
        composable(Screen.Home.rout) {
            HomeScreen(navController)
        }
        composable(Screen.WeatherPage.rout) {
            val viewModel = hiltViewModel<WeatherViewModel>()
            val state = viewModel.weatherUiState.collectAsState().value
            WeatherScreen(navController,
                goBack = {
                    navController.popBackStack()

                },
                onEvent = viewModel::onEvent,
                state = state
            )
        }


    }
}