package com.example.prueba3.ui.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.theme.ColorEnFarmacia
import com.example.prueba3.utils.dosisResaltada
import com.example.prueba3.ui.theme.CustomTypography
import com.example.prueba3.ui.theme.StrokedText
import com.example.prueba3.ui.theme.StyledTratamientoWrapper
import com.example.prueba3.utils.formatearFecha
import com.example.prueba3.utils.sinDecimalInnecesario
import java.time.LocalDate


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TratamientoCard(
    medicina: Medicina,
    modifier: Modifier = Modifier,
    colorFondo: Color = MaterialTheme.colorScheme.surfaceVariant,
    momento: String,
    onClick: () -> Unit,
    onStockUpdate: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var showInput by remember { mutableStateOf(false) }
    var nuevoStockTexto by remember { mutableStateOf("") }


    StyledTratamientoWrapper(
        modifier = modifier,
        colorFondo = colorFondo,
        onClick = onClick,
        onLongClick = { showDialog = true }
    ) {
        ContenidoTratamiento(medicina = medicina, momento = momento)
    }


    if (showDialog) {
        DialogoOpcionesContent (
            medicina = medicina,
            onDismiss = { showDialog = false },
            onAñadirCaja = {
                onStockUpdate(medicina.stockActualizado + medicina.unidadesCaja)
                showDialog = false
            },
            onMostrarInput = {
                showDialog = false
                showInput = true
            }
        )
    }

    if (showInput) {
        DialogoNuevoStockContent (
            nuevoStockTexto = nuevoStockTexto,
            onValueChange = { nuevoStockTexto = it },
            onGuardar = {
                val nuevo = nuevoStockTexto.toIntOrNull()
                if (nuevo != null) {
                    onStockUpdate(nuevo)
                    showInput = false
                }
            },
            onCancelar = { showInput = false }
        )
    }
}

@Composable
fun ContenidoTratamiento(medicina: Medicina, momento: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("💊 ${medicina.name}", style = CustomTypography.labelLarge)
        Text(
            "🔬  ${medicina.principioActivo}",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("📦 : ${medicina.stockActualizado}", style = MaterialTheme.typography.bodyMedium)
            Text(
                buildAnnotatedString {
                    append("🧪 ")
                    append(dosisResaltada(medicina.dosis, momento))
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun AllInfoCard(
    medicina: Medicina,
    modifier: Modifier = Modifier,
    colorFondo: Color = MaterialTheme.colorScheme.surfaceVariant,

    ) {
    val esActiva = medicina.dosis != "0000"
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            EncabezadoMedicamento(medicina.name)
            SeccionSeparador()
            InfoFarmacologica(medicina)
            SeccionSeparador()
            InfoStock2(medicina)
            SeccionSeparador()
            InfoConsumo(medicina)
            SeccionSeparador()
            FechasTratamiento(medicina)
            MensajePedido(
                fecha = if (esActiva) medicina.proximaFechaPedido else null,
                esActiva = esActiva
            )
        }
    }
}


@Composable
fun EncabezadoMedicamento(nombre: String, modifier: Modifier = Modifier) {
    StrokedText(text = nombre)
}

@Composable
fun InfoFarmacologica(medicina: Medicina) {
    StrokedText("🧪 ${medicina.principioActivo}")
    StrokedText(" Unidades por Caja: ${medicina.unidadesCaja} u.")
}

@Composable
fun InfoStock(medicina: Medicina) {
    StrokedText("💊 Dosis:${medicina.dosis}")
    StrokedText("📦 Stock Actual: ${medicina.stockActualizado} u.")
}

@Composable
fun InfoConsumo(
    medicina: Medicina,
) {
    StrokedText("Consumo diario: ${medicina.consumoDiario.sinDecimalInnecesario()}")
    StrokedText("Dura hasta: ${medicina.fechaFinStock.formatearFecha()}")
    StrokedText("Semanas disponibles: ${medicina.semanasPendientes}")
}

@Composable
fun FechaFinTratamiento(medicamento: Medicina) {
    medicamento.fechaFinTratamiento?.let {
        StrokedText("Fin Tto.: ${it.formatearFecha()}")
    }
}

@Composable
fun FechasTratamiento(medicamento: Medicina) {
    medicamento.fechaInicioTratamiento?.let {
        StrokedText("🗓️ Inicio Tto.: ${it.formatearFecha()}")
    }
    medicamento.fechaFinTratamiento?.let {
        StrokedText("🗓️ Fin Tto.: ${it.formatearFecha()}")
    }
    medicamento.fechaStock?.let {
        StrokedText("📥 F. Stock: ${it.formatearFecha()}")
    }
}

@Composable
fun MensajePedido(fecha: LocalDate?, esActiva: Boolean) {
    Spacer(modifier = Modifier.height(8.dp))
    when {
        fecha != null -> {
            StrokedText(
                "Sale el: ${fecha.formatearFecha()}",
                fillColor = ColorEnFarmacia
            )
        }

        !esActiva -> {
            StrokedText(
                "🌑 Medicina inactiva",
                //  style = CustomTypography.bodyMedium,
                fillColor = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFECEFF1)) // Gris claro
                    .padding(8.dp)
            )
        }

        else -> {
            StrokedText(
                "⚠️ No hay más disponible",

                fillColor = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEBEE)) // Rosa alerta
                    .padding(8.dp)
            )
        }
    }
}


@Composable
fun InfoStock2(medicina: Medicina) {
    Text("💊 Dosis: ${medicina.dosis}", style = MaterialTheme.typography.bodyMedium)
    Text(
        "📦 Stock: ${medicina.stock} .Fecha: ${medicina.fechaStock?.formatearFecha()}",
        style = MaterialTheme.typography.bodySmall
    )
    Text("📦 Stock Actual: ${medicina.stockActualizado}", style = MaterialTheme.typography.bodySmall)

}

@Composable
fun CompactInfoCard(
    medicina: Medicina,
    colorFondo: Color,
    enFarmacia: Boolean
) {
    val textoColor = if (enFarmacia) Color.Red else LocalContentColor.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.3f)) {
                Text(medicina.name, style = CustomTypography.bodySmall, color = textoColor)
                Text(
                    medicina.principioActivo,
                    style = CustomTypography.labelSmall,
                    color = textoColor
                )
            }
            Text(
                medicina.proximaFechaPedido?.formatearFecha() ?: "----",
                style = CustomTypography.labelSmall,
                modifier = Modifier.weight(1.3f),
                color = textoColor
            )
            Text(
                medicina.fechaEnFarmacia?.formatearFecha() ?: "----",
                style = CustomTypography.labelSmall,
                modifier = Modifier.weight(1f),
                color = textoColor
            )
        }
    }
}
