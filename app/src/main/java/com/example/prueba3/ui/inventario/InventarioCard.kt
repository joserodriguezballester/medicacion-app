package com.example.prueba3.ui.inventario

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.EncabezadoMedicamento
import com.example.prueba3.ui.cards.FechaFinTratamiento
import com.example.prueba3.ui.cards.InfoConsumo
import com.example.prueba3.ui.cards.InfoFarmacologica
import com.example.prueba3.ui.cards.InfoStock
import com.example.prueba3.ui.cards.MensajePedido
import com.example.prueba3.ui.cards.SeccionSeparador
import com.example.prueba3.ui.theme.BackgroundWrapperInventarioCard
import com.example.prueba3.ui.theme.StrokedText
import com.example.prueba3.ui.theme.colorPorSemanas3
import com.example.prueba3.utils.formatearFecha

@Composable
fun InventarioCard(
    medicina: Medicina,
    modifier: Modifier = Modifier,
    colorFondo: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: (() -> Unit)? = null
) {
    val esActiva = medicina.dosis != "0000"

    BackgroundWrapperInventarioCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        colorFondo = colorFondo
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            EncabezadoMedicamento(medicina.name)
            IndicadorStock(
                actual = medicina.stockActualizado,
                maximo = 100
            )
            SeccionSeparador()
            InfoFarmacologica(medicina)
            SeccionSeparador()
            InfoStock(medicina)
            SeccionSeparador()
            InfoConsumo(medicina)
            SeccionSeparador()
            FechaFinTratamiento(medicina)
            MensajePedido(
                fecha = if (esActiva) medicina.proximaFechaPedido else null,
                esActiva = esActiva,
            )
        }
    }
}


@Composable
fun CompactInventarioCard(
    medicina: Medicina,
    modifier: Modifier = Modifier,
    onMedicamentoClick: (Medicina) -> Unit
) {
    val colorFondo = colorPorSemanas3(medicina.semanasPendientes)
    BackgroundWrapperInventarioCard (
        modifier = modifier
            .clickable { onMedicamentoClick(medicina) }
            .padding(2.dp,6.dp),
        colorFondo = colorFondo
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
        ) {
            // Nombre del medicamento con borde
            StrokedText(
                text = medicina.name,
               modifier = Modifier.weight(2.1f).padding(vertical = 8.dp)
            )
            Spacer(Modifier.width(8.dp))
            StrokedText(
                text = "${medicina.stockActualizado} ",
                modifier = Modifier.weight(.8f),
           )

            Spacer(Modifier.width(6.dp))

            StrokedText(
                text = medicina.fechaFinStock.formatearFecha(),
                modifier = Modifier.weight(1.9f),
          )

            Spacer(Modifier.width(8.dp))
            StrokedText(
                text = "${medicina.semanasPendientes}",
                modifier = Modifier.weight(.7f),
           )
        }
    }
}

