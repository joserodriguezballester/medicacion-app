package com.example.prueba3.ui.general

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.modificartratamiento.ModificarTratamientoCard3
import com.example.prueba3.viewmodel.MedicinaViewModel

@Composable
fun MostrarListaRenovar(
    lista: List<Medicina>,
    viewModel: MedicinaViewModel,
    context: Context
) {
    VisualizarLista(
        titulo = "🩺 Pendientes de Renovar",
        lista = lista,
        viewModel = viewModel,
        context = context,
        cardContent = { medicina -> ModificarTratamientoCard3(medicina = medicina) },
        generarMensaje = { viewModel.generarTextoRenovar(lista) }
    )
}