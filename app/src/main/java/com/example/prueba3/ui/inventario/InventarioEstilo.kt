package com.example.prueba3.ui.inventario

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Colores temáticos por urgencia
fun colorPorSemanas1(semanasPendientes: Int): Color = when {
    semanasPendientes <= 0 -> Color(0xFFFFE0E0) // rojo claro
    semanasPendientes == 1 -> Color(0xFFFFF4C2) // amarillo suave
    semanasPendientes >= 2 -> Color(0xFFD5FFD5) // verde tranquilizador
    else -> Color.LightGray
}

// Animación de escala al seleccionar medicina
@Composable
fun animacionSeleccion(isSeleccionada: Boolean): Float {
    val target = if (isSeleccionada) 1.05f else 1f
    val animSpec = tween<Float>(durationMillis = 300, easing = FastOutSlowInEasing)
    val scale by animateFloatAsState(targetValue = target, animationSpec = animSpec, label = "EscalaSeleccion")
    return scale
}

// Indicador visual de stock restante
@Composable
fun IndicadorStock(
    actual: Int,
    maximo: Int,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4CAF50),
    fondo: Color = Color(0xFFE0E0E0)
) {
    val progreso = actual.coerceAtMost(maximo) / maximo.toFloat()
    LinearProgressIndicator(
        progress = progreso,
        color = color,
        trackColor = fondo,
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .progressSemantics(progreso)
    )
}

// Snackbar visual
@Composable
fun MostrarSnackbar(mensaje: String, snackbarHostState: SnackbarHostState) {
    LaunchedEffect(mensaje) {
        snackbarHostState.showSnackbar(mensaje)
    }
}
