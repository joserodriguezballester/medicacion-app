package com.example.prueba3.ui.modificartratamiento

import android.R.attr.textStyle
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.domain.MedicinaValidator
import com.example.prueba3.ui.cards.BordeAnimado
import com.example.prueba3.ui.cards.BotonGradiente
import com.example.prueba3.ui.cards.BotonGradienteSin
import com.example.prueba3.ui.theme.StrokedText
import com.example.prueba3.ui.theme.colorDeFondoPorDosis
import kotlinx.coroutines.launch


@Composable
fun DialogoEditarDosis(
    medicina: Medicina,
    onDismiss: () -> Unit,
    onGuardar: suspend (String) -> Boolean
) {
    var nuevaDosis by remember { mutableStateOf(medicina.dosis) }
    val scope = rememberCoroutineScope()
    val fondo = colorDeFondoPorDosis(medicina.dosis)
    val esDosisValida = MedicinaValidator.validarDosis(nuevaDosis) == null
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp,
            color = Color.Transparent,
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF90CAF9).copy(alpha = 0.9f), // azul lavanda suave
                            Color.Transparent
                        ),
                        center = Offset(300f, 200f), // ajusta según tamaño del diálogo
                        radius = 800f
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Cyan, Color.Blue, Color.Magenta)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally ) {
                StrokedText(medicina.name,fontSize = 28.sp)
                StrokedText(" (5 para ½)", fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = nuevaDosis,
                    onValueChange = { nuevaDosis = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(100.dp) // Ajusta según el tamaño que necesites para 4 dígitos
                        .background(Color.Transparent.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)), // Fondo semitransparente
                    textStyle = LocalTextStyle.current.copy(fontSize = 24.sp), // Fuente más grande
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.Transparent.copy(alpha = 0.1f),
                        disabledContainerColor = Color.Transparent.copy(alpha = 0.1f),focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.End) {
                    BotonGradiente(texto = "Cancelar", onClick = onDismiss, esPrincipal = false)
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .widthIn(min = 100.dp)
                            .animateContentSize()
                            .alpha(if (esDosisValida) 1f else 0.3f) // 👈 semi-visible si no es válido
                            .border(
                                width = 2.dp,
                                brush = BordeAnimado(esDosisValida),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        BotonGradienteSin(
                            texto = "Guardar",
                            onClick = {
                                scope.launch {
                                    val fueExitoso = onGuardar(nuevaDosis)
                                    println("-------$fueExitoso")
                                    if (fueExitoso) onDismiss()
                                }
                            },
                            esPrincipal = true
                        )
                    }

//                    TextButton(onClick = {
//                        scope.launch {
//                            val fueExitoso = onGuardar(nuevaDosis)
//                           println("-------$fueExitoso")
//                            if (fueExitoso) onDismiss()
//                        }
//                    }) {
//                        Text("Guardar")
//                    }
                }
            }
        }
    }
}
