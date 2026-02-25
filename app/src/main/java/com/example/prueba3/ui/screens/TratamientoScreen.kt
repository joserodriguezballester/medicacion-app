package com.example.prueba3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.ui.cards.TratamientoCard
import com.example.prueba3.ui.theme.StrokedText
import com.example.prueba3.ui.theme.colorPorSemanas3
import com.example.prueba3.utils.emojiDelMomento
import com.example.prueba3.utils.momentos
import com.example.prueba3.viewmodel.MedicinaViewModel


@Composable
fun TratamientoScreen(viewModel: MedicinaViewModel, innerPadding: PaddingValues) {
    val listaMedicinas by viewModel.medicinas.collectAsState()
    val tratamientosPorMomento = remember(listaMedicinas) {
        mutableMapOf<String, MutableList<Pair<Medicina, Int>>>().apply {
            momentos.forEach { put(it, mutableListOf()) }
            listaMedicinas.forEach { medicina ->
                val dosis = medicina.dosis.padStart(4, '0')
                dosis.forEachIndexed { index, c ->
                    val cantidad = c.digitToInt()
                    if (cantidad > 0) {
                        val momento = momentos.getOrNull(index)
                        momento?.let { get(it)?.add(medicina to cantidad) }
                    }
                }
            }
        }
    }
    val medicamentosPreparados = remember { mutableStateListOf<Int>() }
    val mensaje by viewModel.mensaje.collectAsState()

    MensajeToastListener(
        mensaje = mensaje,
        onMensajeMostrado = { viewModel.limpiarMensaje() }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 12.dp)
    ) {
        tratamientosPorMomento.forEach { (momento, lista) ->
            if (lista.isNotEmpty()) {
                item {
                    StrokedText(
                        text = "${emojiDelMomento(momento)} $momento",
                        fontSize=27.sp,
                        strokeColor = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colorStops = arrayOf(
                                        0.0f to Color(0xFF00BCD4), // Cyan al inicio
                                        0.8f to Color(0xFF3F51B5), // Indigo ocupa más espacio
                                        1.0f to Color(0xFF681C9F)  // Purple al final
                                    )
                                )
                            )
                            .padding(8.dp)
                    )
                }
                // Agrupa los items manualmente de 2 en 2 para simular un grid
                items(lista.chunked(2)) { par ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        par.forEach { (medicina, cantidad) ->
                            val yaPreparado = medicamentosPreparados.contains(medicina.id)
                            val modifier = Modifier
                                .weight(1f)
                                .graphicsLayer {
                                    alpha = if (yaPreparado) 0.4f else 1f
                                    scaleX = if (yaPreparado) 0.90f else 1f
                                    scaleY = if (yaPreparado) 0.90f else 1f
                                }

                            TratamientoCard(
                                medicina = medicina,
                                modifier = modifier,
                                colorFondo = colorPorSemanas3(medicina.semanasPendientes),
                                momento = momento,
                                onClick = {
                                    if (yaPreparado) {
                                        medicamentosPreparados.remove(medicina.id)
                                    } else {
                                        medicamentosPreparados.add(medicina.id)
                                    }
                                },
                                onStockUpdate = { nuevoStock ->
                                 viewModel.actualizarStock(medicina.id,nuevoStock)
                                },
                            )
                            // Si el grupo es impar, añade un Spacer como "relleno"
                            if (par.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                }
            }
        }
    }
}
