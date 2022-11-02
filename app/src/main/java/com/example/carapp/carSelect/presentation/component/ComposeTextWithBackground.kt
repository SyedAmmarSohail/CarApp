package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.carapp.ui.theme.White
import com.example.carapp.ui.theme.spacing

@Composable
fun ComposeTextWithBackground(
    modifier: Modifier = Modifier,
    text: String = "",
    radius: Dp = MaterialTheme.spacing.view_2x,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(color = White),
    backgroundColor: Color = White,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = MaterialTheme.spacing.view_4x,
                    vertical = MaterialTheme.spacing.view_2x
                )

        )
    }
}