package com.example.prueba3.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.ui.inventario.CompactInventarioCard
import com.example.prueba3.ui.inventario.DialogosInventario
import com.example.prueba3.ui.inventario.InventarioCard
import com.example.prueba3.ui.inventario.InventarioController
import com.example.prueba3.ui.theme.CustomTypography
import com.example.prueba3.ui.theme.colorPorSemanas3
import com.example.prueba3.viewmodel.MedicinaViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InventarioScreen(viewModel: MedicinaViewModel, innerPadding: PaddingValues) {
    val controller = remember { InventarioController(viewModel) }
    val estado = controller.uiState.value
    val modoCompacto by viewModel.modoCompacto.collectAsState()
    val medicinasActivasOrd by viewModel.medicinasActivasOrdenadas.collectAsState()

    val mensaje by viewModel.mensaje.collectAsState()

    MensajeToastListener(
        mensaje = mensaje,
        onMensajeMostrado = { viewModel.limpiarMensaje() }
    )

    DialogosInventario(
        medicinaSeleccionada = estado.medicinaSeleccionada,
        tipoDialogo = estado.tipoDialogo,
        nuevoStockTexto = estado.nuevoStockTexto,
        onUpdateTexto = { controller.actualizarTextoStock(it) },
        onMostrarInput = { controller.mostrarInputStock() },
        onCancelar = { controller.cerrarDialogos() },
        onActualizarStock = { stock -> controller.actualizarStock(stock) },
        onAñadirCaja = { controller.añadirCaja() },
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BCD4), // Cyan
                        Color(0xFF3F51B5), // Indigo
                        Color(0xFF9C27B0)  // Purple
                    )
                )
            )
    ) {
        if (modoCompacto) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                stickyHeader {
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            "💊 ",
                            modifier = Modifier.weight(1.9f),
                            style = CustomTypography.bodyMedium
                        )
                        Text(
                            "📦 ",
                            modifier = Modifier.weight(1f),
                            style = CustomTypography.bodyMedium
                        )
                        Text(
                            "📆 Fin stock",
                            modifier = Modifier.weight(1.6f),
                            style = CustomTypography.bodyMedium
                        )
                        Text(
                            "⏳ ",
                            modifier = Modifier.weight(.5f),
                            style = CustomTypography.bodyMedium
                        )
                    }
                }
                items(medicinasActivasOrd) { medicina ->
                    CompactInventarioCard(
                        medicina,
                        modifier = Modifier
                            .fillMaxSize()
                        //          .padding(innerPadding)
                        //         .padding(16.dp),
                        , onMedicamentoClick = { controller.seleccionarMedicina(it) }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                items(medicinasActivasOrd) { medicina ->
                    InventarioCard(
                        medicina = medicina,
                        colorFondo = colorPorSemanas3(medicina.semanasPendientes),
                        onClick = { controller.seleccionarMedicina(medicina) }
                    )
                }
            }
        }
    }

}

