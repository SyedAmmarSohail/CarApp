package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import com.example.carapp.ui.theme.Blue
import com.example.carapp.ui.theme.spacing

@Composable
fun ComposeButton(
    modifier: Modifier = Modifier,
    fraction: Float = 1f,
    buttonText: String = "",
    backgroundColor: Color = Blue,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier
            .fillMaxWidth(fraction)
            .clip(RoundedCornerShape(MaterialTheme.spacing.view_2x)).testTag("testSummaryButton"),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(if (enabled) backgroundColor else Blue),
        enabled = enabled
    ) {
        Text(text = buttonText, textAlign = TextAlign.Center, color = textColor, style = MaterialTheme.typography.button)
    }
}