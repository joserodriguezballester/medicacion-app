package com.example.prueba3.ui.theme

import android.R.attr.fillColor
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StrokedText(
    text: String,
    fontSize: TextUnit = 24.sp,
   // strokeColor: Color = BordeCyan,
  //  fillColor: Color = Color.Gray,
    strokeColor: Color = BordeStroke,
    fillColor: Color = TextoColor,
    modifier: Modifier= Modifier
) {
    Box (modifier = modifier) {
        // Capa de borde
        Text(
            text = text,
            fontSize = fontSize,
            style = TextStyle(
                drawStyle = Stroke(width = 4f),
                color = strokeColor,
                fontWeight = FontWeight.Bold
            )
        )
        // Capa de relleno
        Text(
            text = text,
            fontSize = fontSize,
            style = TextStyle(
                color = fillColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


//val FondoCampo = Color(0xFFD0D8E8) // gris azulado más marcado

@Composable
fun coloresCampoTexto(): TextFieldColors {
    val FondoCampo = Color(0xFFB0C4DE)
    val bordeCyan = Color(0xFF00FFFF) // cyan vibrante
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = bordeCyan,
        unfocusedBorderColor = bordeCyan,
        errorBorderColor = Color.Red,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.DarkGray,
        errorLabelColor = Color.Red,
        focusedContainerColor = FondoCampo,
        unfocusedContainerColor = FondoCampo,
        errorContainerColor = FondoCampo,
        cursorColor = Color.Black,
        errorCursorColor = Color.Red
    )
}
//val borderStyle = OutlinedTextFieldDefaults.borders(
//    focusedBorderThickness = 2.dp,
//    unfocusedBorderThickness = 1.dp,
//    errorBorderThickness = 2.dp,
//    shape = RoundedCornerShape(12.dp)
//)



@Composable
fun Modifier.borde2dp(
    color: Color =   Color(0xFF00BCD4),
    shape: Shape = RoundedCornerShape(8.dp)
): Modifier = this.then(
    Modifier.border(width = 2.dp, color = color, shape = shape)
)
