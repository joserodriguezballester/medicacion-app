package com.example.prueba3.ui.theme

import android.R.attr.fontFamily
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography.titleMonentos: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 24.sp
    )
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

)

val CustomTypography = Typography(

    titleLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle( // lo usas como bodyMediumBold
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    bodySmall = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),

)