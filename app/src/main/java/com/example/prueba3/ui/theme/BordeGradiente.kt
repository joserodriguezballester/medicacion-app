package com.example.prueba3.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.bordeGradiente25(): Modifier {
    return this.then(
        Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF00BCD4),
                    Color(0xFF3F51B5),
                    Color(0xFF9C27B0)
                )
            ),
            shape = RoundedCornerShape(25.dp)
        )
    )
}

fun Modifier.bordeGradiente8(): Modifier {
    return this.then(
        Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF00BCD4),
                    Color(0xFF3F51B5),
                    Color(0xFF9C27B0)
                )
            ),
            shape = RoundedCornerShape(25.dp)
        )
    )
}