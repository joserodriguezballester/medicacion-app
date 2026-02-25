package com.example.prueba3.ui.general

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prueba3.model.GrupoCompra
import com.example.prueba3.ui.inventario.CompactInventarioCard
import com.example.prueba3.ui.theme.WhatsAppGreen
import com.example.prueba3.viewmodel.MedicinaViewModel


@Composable
fun MostrarListaCompra(
    lista: GrupoCompra,
    viewModel: MedicinaViewModel,
    context: Context,
    innerPadding: PaddingValues
) {
    Text("🛒 Lista de Compra", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(12.dp))

    // ✅ Lista de medicamentos EN farmacia
    VisualizarLista(
        titulo = "✅ EN farmacia",
        lista = lista.enFarmacia,
        viewModel = viewModel,
        context = context,
        cardContent = { medicina ->
            CompactInventarioCard(
                medicina = medicina,
                modifier = Modifier.fillMaxSize(),
                onMedicamentoClick = {}
            )
        },
        generarMensaje = { viewModel.generarTextoListaCompra() }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // ❌ Lista de medicamentos NO en farmacia
    VisualizarLista(
        titulo = "❌ NO en farmacia",
        lista = lista.noEnFarmacia,
        viewModel = viewModel,
        context = context,
        cardContent = { medicina ->
            CompactInventarioCard(
                medicina = medicina,
                modifier = Modifier.fillMaxSize(),
                onMedicamentoClick = {}
            )
        },
        generarMensaje = { viewModel.generarTextoListaCompra() }
    )
}
