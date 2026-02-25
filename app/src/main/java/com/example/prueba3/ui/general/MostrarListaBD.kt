package com.example.prueba3.ui.general

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.AllInfoCard
import com.example.prueba3.viewmodel.MedicinaViewModel

@Composable
fun MostrarListaBD(
    lista: List<Medicina>,
    viewModel: MedicinaViewModel,
    context: Context
) {
    VisualizarLista(
        titulo = "\uD83D\uDCCB Lista de la Base de Datos",
        lista = lista,
        viewModel = viewModel,
        context = context,
        cardContent = { medicina -> AllInfoCard(medicina = medicina) },
        generarMensaje = { viewModel.generarTextoBD(lista) }
    )
}