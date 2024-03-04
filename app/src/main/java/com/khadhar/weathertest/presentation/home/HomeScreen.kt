package com.khadhar.weathertest.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khadhar.weathertest.R
import com.khadhar.weathertest.presentation.weatherScreen.componentes.CustomButton
import com.khadhar.weathertest.ui.theme.PeriwinkleBlue
import com.khadhar.weathertest.utils.Screen

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp).align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.welcome_to_the_Weather_app),
            style = MaterialTheme.typography.headlineMedium
            , color = PeriwinkleBlue
        )
        Spacer(modifier = Modifier.height(50.dp))


        CustomButton(modifier = Modifier.fillMaxWidth().height(60.dp),
            text = stringResource(R.string.see_the_weather),
            onClick = {
                navController.navigate(Screen.WeatherPage.rout)
            })
    }
}



