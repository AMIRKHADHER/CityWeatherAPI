package com.khadhar.weathertest.presentation.weatherScreen.componentes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
    height: Dp = ButtonDefaults.MinHeight,
    fontSize: TextUnit = 26.sp,
    horizontalPadding: Dp = ButtonDefaults.IconSpacing,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit
) {
    val currentFocus = LocalFocusManager.current

    Button(
        modifier = modifier.background(backgroundColor)
            .height(height),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        ),
        onClick = {
            currentFocus.clearFocus()
            onClick()
        }
    ) {
        Spacer(Modifier.size(horizontalPadding))

        Text(
            modifier = Modifier
                .padding(
                    start = if (startIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding,
                    end = if (endIcon != null) 0.dp else ButtonDefaults.IconSize + horizontalPadding
                )
                .weight(1f),
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


    }
}