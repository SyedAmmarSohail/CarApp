package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.carapp.ui.theme.spacing
import kotlin.random.Random

@Composable
fun LinePlaceHolder(
) = ShimmerAnimation {
    Spacer(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(MaterialTheme.spacing.view_10x)
            .padding(vertical = MaterialTheme.spacing.view_3x)
            .background(
                brush = it,
                shape = MaterialTheme.shapes.small
            )
    )
}