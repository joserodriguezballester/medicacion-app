package com.example.prueba3.ui.inventario

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.DialogoNuevoStock
import com.example.prueba3.ui.cards.DialogoNuevoStockContent
import com.example.prueba3.ui.cards.DialogoOpcionesContent

@Composable
fun DialogosInventario(
    medicinaSeleccionada: Medicina?,
    tipoDialogo: TipoDialogo,
    nuevoStockTexto: String,
    onUpdateTexto: (String) -> Unit,
    onActualizarStock: (Int) -> Unit,
    onAñadirCaja: () -> Unit,
    onMostrarInput: () -> Unit,
    onCancelar: () -> Unit
) {
    medicinaSeleccionada?.let { medicina ->
        when (tipoDialogo) {
            TipoDialogo.Opciones -> {
                Dialog(onDismissRequest = onCancelar) {
                    DialogoOpcionesContent(
                        medicina = medicina,
                        onDismiss = onCancelar,
                        onAñadirCaja = onAñadirCaja,
                        onMostrarInput = onMostrarInput
                    )
                }
            }

            TipoDialogo.NuevoStock -> {
                Dialog(onDismissRequest = onCancelar) {
                    DialogoNuevoStockContent(
                        nuevoStockTexto = nuevoStockTexto,
                        onValueChange = onUpdateTexto,
                        onGuardar = {
                            nuevoStockTexto.toIntOrNull()?.let {
                                onActualizarStock(it)
                                onCancelar()
                            }
                        },
                        onCancelar = onCancelar
                    )
                }
            }
            TipoDialogo.Ninguno -> {
                // No se muestra ningún diálogo
            }
        }
    }
}