package com.example.carapp.carSelect.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.carapp.R
import com.example.carapp.ui.theme.*

@Composable
fun CarSummaryScreen(
    manufacturer: String,
    mainType: String,
    builtDate: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(start = MaterialTheme.spacing.view_4x),
    ) {
        Text(
            text = manufacturer,
            style = MaterialTheme.typography.h3.copy(
                color = DarkGray,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = mainType,
            style = MaterialTheme.typography.body1.copy(
                color = Gray,
                fontSize = MaterialTheme.fontSize.view_14x
            )
        )
        Text(
            text = builtDate,
            style = MaterialTheme.typography.body1.copy(
                color = Orange,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.fontSize.view_14x
            )
        )

        Image(
            painter = painterResource(id = R.drawable.car_sample),
            contentDescription = "CarDummyImage",
            Modifier
                .aspectRatio(1.961136f)
        )
    }
}