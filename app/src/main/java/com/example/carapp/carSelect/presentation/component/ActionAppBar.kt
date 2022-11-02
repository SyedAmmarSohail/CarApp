package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.carapp.ui.theme.White
import com.example.carapp.ui.theme.spacing

@Composable
fun ActionAppBar(
    title: String,
    textStyle: TextStyle = TextStyle(),
    modifier: Modifier = Modifier
) {
    TopAppBar(
        elevation = MaterialTheme.spacing.view_1x,
        backgroundColor = White,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}