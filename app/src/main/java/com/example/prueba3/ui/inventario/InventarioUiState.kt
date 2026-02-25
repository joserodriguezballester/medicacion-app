package com.example.prueba3.ui.inventario

import com.example.prueba3.data.local.entity.Medicina

data class InventarioUiState(
    val medicinaSeleccionada: Medicina? = null,
    val tipoDialogo: TipoDialogo = TipoDialogo.Ninguno,
    val nuevoStockTexto: String = ""
)
