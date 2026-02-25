package com.example.prueba3.ui.modificartratamiento

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.theme.CustomTypography
import com.example.prueba3.ui.theme.StrokedText
import com.example.prueba3.ui.theme.colorDeFondoPorDosis
import com.example.prueba3.utils.formatearDosis
import com.example.prueba3.utils.formatearFecha

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModificarTratamientoCard(
    medicina: Medicina,
    onEditar: (Medicina) -> Unit,
    onResetear: (Medicina) -> Unit
) {
    val colorFondo = colorDeFondoPorDosis(medicina.dosis)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onEditar(medicina) },
                onLongClick = { onResetear(medicina) }
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Cyan.copy(alpha = 0.2f),
                spotColor = Color.Blue.copy(alpha = 0.4f)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorFondo.copy(alpha = 0.9f),
                        Color.Transparent
                    )
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
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StrokedText(
                text = "💊 ${medicina.name}",
                fontSize = 26.sp,
                modifier = Modifier.weight(1f)
            )
            StrokedText(
                text = medicina.dosis.formatearDosis(),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}



@Composable
fun ModificarTratamientoCard2(
    medicina: Medicina,

) {
    val colorFondo = colorDeFondoPorDosis(medicina.dosis)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StrokedText(
                text = "💊 ${medicina.name}",
                modifier = Modifier.weight(1f)
            )
            StrokedText(
                text = medicina.dosis.formatearDosis(),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
@Composable
fun ModificarTratamientoCard3(
    medicina: Medicina,

    ) {
    val colorFondo = colorDeFondoPorDosis(medicina.dosis)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "💊 ${medicina.name}",
                style = CustomTypography.labelLarge,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = medicina.fechaFinTratamiento!!.formatearFecha() ,
                style = CustomTypography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}