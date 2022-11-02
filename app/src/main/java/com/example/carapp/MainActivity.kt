package com.example.carapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.carapp.carSelect.presentation.CarScreen
import com.example.carapp.ui.theme.CarAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CarScreen()
                }
            }
        }
    }
}