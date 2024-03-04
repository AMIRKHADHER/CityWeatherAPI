package com.khadhar.weathertest.presentation.weatherScreen.componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier,
    goBack: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = goBack
        ) {
            Icon(
                Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Go Back"
            )
        }
        Text(
            modifier = modifier
                .padding(
                    start = 0.dp,
                    top = 32.dp,
                    end = 48.dp,
                    bottom = 32.dp
                )
                .fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}
