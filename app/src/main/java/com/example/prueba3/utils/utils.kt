package com.example.prueba3.utils


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun colorPorSemanas(semanas: Int): Color = when (semanas) {
  //  0 -> Color(0xFFEF555) //morado claro
   // 0 -> Color(0xFFD32F2F) //morado claro
    0 -> Color(0xFFEF5555)
    1 -> Color(0xFFEF5566) //  rojo claro
    2 -> Color(0xFFFFF9C4) // amarillo claro
    else -> Color(0xFFC8E6C9) // verde claro
}


fun colorPorSemanas2(semanasPendientes: Int): Color {
    return when {
        semanasPendientes <= 0 -> Color(0xFFFFD4D4) // Rojo suave
        semanasPendientes == 1 -> Color(0xFFFFF7C0) // Amarillo claro
        semanasPendientes >= 2 -> Color(0xFFE2FFD4) // Verde calmado
        else -> Color.LightGray
    }
}

//fun formatearDosis(dosis: String): String {
//    return dosis
//        .padStart(4, '0')
//        .map { if (it == '5') "½" else it.toString() }
//        .joinToString("")
//}
fun String.formatearDosis(): String {
    return this
        .padStart(4, '0')
        .map { if (it == '5') "½" else it.toString() }
        .joinToString("")
}

val momentos = listOf("Desayuno", "Comida", "Cena", "Resopón")
fun emojiDelMomento(momento: String): String = when (momento) {
    "Desayuno" -> "🌅"
    "Comida" -> "🍽️"
    "Cena" -> "🌇"
    "Resopón" -> "🌙"
    else -> "🕑"
}

fun indexDeMomento(momento: String): Int = momentos.indexOf(momento)


@Composable
fun dosisResaltada(dosis: String, momento: String): AnnotatedString {
    val momentoIndex = indexDeMomento(momento)
    val dosisFormateada = dosis.padStart(4, '0').map {
        if (it == '5') "½" else it.toString()
    }

    val highlightColor = MaterialTheme.colorScheme.primaryContainer
    val highlightTextColor = MaterialTheme.colorScheme.onPrimaryContainer

    return buildAnnotatedString {
        dosisFormateada.forEachIndexed { index, c ->
            withStyle(
                style = if (index == momentoIndex) {
                    SpanStyle(
                        background = highlightColor,
                        color = highlightTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                } else {
                    SpanStyle(fontSize = 14.sp)
                }
            ) {
                append(c)
            }
        }
    }
}

fun LocalDate.formatearFecha(): String {
    val formatoBonito = DateTimeFormatter.ofPattern("dd/MM/yy")
    return this.format(formatoBonito)
}

fun String.pasarAFechaddMMyyyy(patron: String = "dd/MM/yyyy"): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(patron)
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}
fun Double.sinDecimalInnecesario(): String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        "%.1f".format(this)
    }
}