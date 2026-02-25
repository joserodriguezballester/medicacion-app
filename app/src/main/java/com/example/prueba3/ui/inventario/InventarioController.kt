package com.example.prueba3.ui.inventario

import androidx.compose.runtime.mutableStateOf
import com.example.prueba3.data.local.entity.Medicina
import androidx.compose.runtime.*
import com.example.prueba3.viewmodel.MedicinaViewModel

class InventarioController(
    private val viewModel: MedicinaViewModel,
    initialState: InventarioUiState = InventarioUiState()) {

    private val _uiState = mutableStateOf(initialState)
    val uiState: State<InventarioUiState> = _uiState

    fun seleccionarMedicina(medicina: Medicina) {
        _uiState.value = _uiState.value.copy(
            medicinaSeleccionada = medicina,
            tipoDialogo = TipoDialogo.Opciones
        )
    }

    fun mostrarInputStock() {
        _uiState.value = _uiState.value.copy(tipoDialogo = TipoDialogo.NuevoStock)
    }

    fun cerrarDialogos() {
        _uiState.value = _uiState.value.copy(
            tipoDialogo = TipoDialogo.Ninguno,
            medicinaSeleccionada = null
        )
    }

    fun actualizarTextoStock(nuevoTexto: String) {
        _uiState.value = _uiState.value.copy(nuevoStockTexto = nuevoTexto)
    }
    fun actualizarStock(nuevo: Int) {
        val medicina = _uiState.value.medicinaSeleccionada ?: return
        viewModel.actualizarStock(medicina.id, nuevo)
        cerrarDialogos()
    }
    fun añadirCaja() {
        val medicina = _uiState.value.medicinaSeleccionada ?: return
        val nuevoStock = medicina.stockActualizado + medicina.unidadesCaja
        viewModel.actualizarStock(medicina.id, nuevoStock)
        cerrarDialogos()
    }



}