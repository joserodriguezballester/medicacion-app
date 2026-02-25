package com.example.prueba3.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val ColorEnFarmacia = Color(0xFFFFE5E5)
val BordeCyan = Color(0xFF00FFFF) // cyan vibrante
val WhatsAppGreen = Color(0xFF25D366)

fun colorPorSemanas3(semanas: Int): Color = when (semanas) {
    0 -> Color(0xFFD50000) // rojo intenso
    1 -> Color(0xFFFF6F00) // naranja vibrante
    2 -> Color(0xFFFFEB3B) // amarillo puro
    else -> Color(0xFF00C853) // verde brillante
}


fun colorDeFondoPorDosis(dosis: String): Color {
    return if (dosis == "0000") {
        Color(0xFFB0BEC5)
      //  Color(0xFFEEEEEE) // gris claro para desactivado
    } else {
        Color(0xFF90CAF9)
    //    Color(0xFFEEEEEE)// lavanda suave o cualquier tono pastel activo
    }
}

val TextoColor=Color(0xFFE0E0E0)
val BordeStroke=Color(0xFF444444)
