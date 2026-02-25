package com.example.prueba3.ui.general

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.theme.WhatsAppGreen
import com.example.prueba3.viewmodel.MedicinaViewModel

@Composable
fun VisualizarLista(
    titulo: String,
    lista: List<Medicina>,
    viewModel: MedicinaViewModel,
    context: Context,
    cardContent: @Composable (Medicina) -> Unit,
    generarMensaje: () -> String
) {
    Text(titulo, style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(12.dp))

    lista.forEach { medicina ->
        cardContent(medicina)
        Spacer(modifier = Modifier.height(4.dp))
    }

    Spacer(modifier = Modifier.height(16.dp))
    val mensaje = remember(lista) { generarMensaje() }
    LaunchedEffect(mensaje) {
        viewModel.actualizarMensajeCompartir(mensaje)
    }
}


