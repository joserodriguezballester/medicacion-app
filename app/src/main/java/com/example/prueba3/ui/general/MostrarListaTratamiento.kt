package com.example.prueba3.ui.general

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.modificartratamiento.ModificarTratamientoCard2
import com.example.prueba3.viewmodel.MedicinaViewModel

@Composable
fun MostrarListaTratamiento(
    lista: List<Medicina>,
    viewModel: MedicinaViewModel,
    context: Context
) {
    VisualizarLista(
        titulo = "🩺 Lista de Tratamiento",
        lista = lista,
        viewModel = viewModel,
        context = context,
        cardContent = { medicina -> ModificarTratamientoCard2(medicina = medicina) },
        generarMensaje = { viewModel.generarTextoTratamiento(lista) }
    )
}