package com.example.carapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.carapp.R

val openSans = FontFamily(
    listOf(
        Font(R.font.open_sans_regular, FontWeight.Normal),
        Font(R.font.open_sans_medium, FontWeight.Medium),
        Font(R.font.open_sans_semi_bold, FontWeight.SemiBold),
        Font(R.font.open_sans_bold, FontWeight.Bold),
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    caption = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = openSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
)