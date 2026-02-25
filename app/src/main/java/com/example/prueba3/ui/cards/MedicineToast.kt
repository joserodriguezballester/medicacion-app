package com.example.prueba3.ui.cards


import android.R.attr.end
import android.R.attr.onClick
import android.R.attr.startX
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.theme.CustomTypography
import com.example.prueba3.ui.theme.bordeGradiente25
import com.example.prueba3.ui.theme.bordeGradiente8
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MensajeToastListener(
    mensaje: String?,
    onMensajeMostrado: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(mensaje) {
        println("MensajeToastListener recibió: $mensaje")
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            delay(500)
            onMensajeMostrado()
        }
    }
}


@Composable
fun DialogoConfirmarIncrementoStock(
    medicina: Medicina,
    onDismiss: () -> Unit,
    onConfirmar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar acción") },
        text = {
            Text("¿Quieres aumentar el stock de ${medicina.name} en ${medicina.unidadesCaja} unidades?")
        },
        confirmButton = {
            TextButton(onClick = onConfirmar) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DialogoNuevoStock1(
    nuevoStockTexto: String,
    onValueChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Nuevo stock") },
        text = {
            Column {
                Text("Introduce el nuevo valor de stock:")
                TextField(
                    value = nuevoStockTexto,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onGuardar) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar") }
        }
    )
}

@Composable
fun DialogoNuevoStock(
    nuevoStockTexto: String,
    onValueChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    Dialog(onDismissRequest = onCancelar) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF3F51B5),
                            Color(0xFF9C27B0),
                            Color(0xFF00BCD4)
                        ),
                        center = Offset(300f, 200f),
                        radius = 600f
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Nuevo stock",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = nuevoStockTexto,
                    onValueChange = onValueChange,
                    label = { Text("Cantidad") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color(0x33000000),
                        unfocusedContainerColor = Color(0x33000000),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        cursorColor = Color.White
                    )
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botón Cancelar (secundario)
                    Box(modifier = Modifier.bordeGradiente25()) {
                        Button(
                            onClick = onCancelar,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Text("Cancelar")
                        }
                    }

                    Spacer(Modifier.width(8.dp))

                    // Botón Guardar (principal)
                    Box(modifier = Modifier.bordeGradiente25()) {
                        Button(
                            onClick = onGuardar,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50), // Verde aceptación
                                contentColor = Color.White
                            )
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SeccionSeparador(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
    thickness: Dp = 1.dp,
    paddingVertical: Dp = 8.dp
) {
    HorizontalDivider(
        modifier = modifier.padding(vertical = paddingVertical),
        thickness = thickness,
        color = color
    )
}


@Composable
fun DialogoOpcionesContent(
    medicina: Medicina,
    onDismiss: () -> Unit,
    onAñadirCaja: () -> Unit,
    onMostrarInput: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BCD4),
                        Color(0xFF3F51B5),
                        Color(0xFF9C27B0)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Opciones para ${medicina.name}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .bordeGradiente25()
            ) {
                Button(
                    onClick = onAñadirCaja,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir una caja \n(${medicina.unidadesCaja} unidades)")
                }
            }
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .bordeGradiente25()
            ) {
                Button(onClick = onMostrarInput, modifier = Modifier.fillMaxWidth()) {
                    Text("Actualizar stock")
                }
            }
            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .bordeGradiente25()
            ) {
                Text("Cerrar", Modifier.padding(horizontal = 12.dp), color = Color.White)
            }
        }
    }
}


@Composable
fun DialogoNuevoStockContent(
    nuevoStockTexto: String,
    onValueChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    val esNumeroValido = nuevoStockTexto.toIntOrNull() != null
    val esVisible by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3F51B5),
                        Color(0xFF9C27B0),
                        Color(0xFF00BCD4)
                    ),
                    center = Offset(300f, 200f),
                    radius = 600f
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Nuevo stock",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))
            TextField(
                value = nuevoStockTexto,
                onValueChange = onValueChange,
                label = { Text("Cantidad") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0x33000000),
                    unfocusedContainerColor = Color(0x33000000),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    cursorColor = Color.White
                ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                BotonGradiente(texto = "Cancelar", onClick = onCancelar, esPrincipal = false)
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .widthIn(min = 100.dp)
                        .animateContentSize()
                        .alpha(if (esNumeroValido) 1f else 0.3f) // 👈 semi-visible si no es válido
                        .border(
                            width = 2.dp,
                            brush = BordeAnimado(esNumeroValido),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    BotonGradienteSin(texto = "Guardar", onClick = onGuardar, esPrincipal = true)
                }
            }
        }
    }
}

@Composable
fun BordeAnimado(esActivo: Boolean): Brush {
    val infinito = rememberInfiniteTransition()
    val desplazamiento by infinito.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    return if (esActivo) {
        Brush.linearGradient(
            colors = listOf(
                Color.Cyan,
                Color.Magenta,
                Color.Cyan
            ),
            start = Offset(0f, 0f),
            end = Offset(desplazamiento, desplazamiento)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Gray.copy(alpha = 0.3f), Color.Gray.copy(alpha = 0.3f))
        )
    }
}

@Composable
fun BotonGradiente(
    texto: String,
    onClick: () -> Unit,
    esPrincipal: Boolean
) {
    val shape = RoundedCornerShape(12.dp)

    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    // Animación de pulso (alpha)
    val alphaPulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var size by remember { mutableStateOf(IntSize.Zero) }

    val gradienteAnimado = if (size.width > 0) {
        val startX = offset * size.width
        val endX = startX + size.width / 2f
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF00B0FF), // Azul vibrante
                Color(0xFFB2FF59), // Verde lima brillante
             //   Color(0xFF1DE9B6),
                Color(0xFFFFFFFF), // Blanco para resplandor
                Color(0xFF1DE9B6), // Verde agua
                Color(0xFF00B0FF),
          //      Color(0xFFFFFFFF)
            ),// Azul vibrante
            start = Offset(startX, 0f),
            end = Offset(endX, 0f)
        )
    } else {
        Brush.linearGradient(colors = listOf(Color.Gray, Color.LightGray))
    }
    Box(
        modifier = Modifier
            .onSizeChanged { size = it }
            .border(
                width = 3.dp,
                brush = if (esPrincipal)
                    gradienteAnimado
                else
                    Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.LightGray)
                )
                ,
                shape = shape
            )
            .graphicsLayer {
                alpha = if (esPrincipal) alphaPulse else 1f
            }
            .clip(shape)
            .background(Color.Transparent)
    ) {
        Button(
            onClick = onClick,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        ) {
            Text(texto)
        }
    }
}

@Composable
fun BotonGradienteSin(
    texto: String,
    onClick: () -> Unit,
    esPrincipal: Boolean
) {
    val shape = RoundedCornerShape(12.dp)

    val bordeBrush = if (esPrincipal) {
        Brush.linearGradient(
            colors = listOf(Color(0xFF00B0FF), Color(0xFF1DE9B6))
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Gray, Color.LightGray)
        )
    }

    Box(
        modifier = Modifier
            .border(width = 2.dp, brush = bordeBrush, shape = shape)
            .clip(shape)
            .background(Color.Transparent)
    ) {
        Button(
            onClick = onClick,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        ) {
            Text(texto)
        }
    }
}
