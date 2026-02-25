package com.example.prueba3.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.ui.modificartratamiento.DialogoEditarDosis
import com.example.prueba3.ui.modificartratamiento.ModificarTratamientoCard
import com.example.prueba3.viewmodel.MedicinaViewModel
import kotlinx.coroutines.launch

@Composable
fun ModificarTratamientoScreen(
    viewModel: MedicinaViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val listaMedicamentos by viewModel.medicinas.collectAsState()
    var medicamentoEnEdicion by remember { mutableStateOf<Medicina?>(null) }
    val context = LocalContext.current
    val listaOrdenada = remember(listaMedicamentos) {
        listaMedicamentos.sortedByDescending { it.dosis.toIntOrNull() ?: 0 }
    }
    val scope = rememberCoroutineScope()
    val mensaje by viewModel.mensaje.collectAsState()

    MensajeToastListener(
        mensaje = mensaje,
        onMensajeMostrado = { viewModel.limpiarMensaje() }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(listaOrdenada) { medicina ->
            ModificarTratamientoCard(
                medicina = medicina,
                onEditar = { medicamentoEnEdicion = it },
                onResetear = { viewModel.actualizarDosis(it.id, "0000") }
            )
        }
    }
    medicamentoEnEdicion?.let { medicina ->
        DialogoEditarDosis(
            medicina = medicina,
            onDismiss = { medicamentoEnEdicion = null },
            onGuardar = { nuevaDosis ->
                viewModel.validarYActualizarDosis(medicina.id, nuevaDosis)
            }
        )
    }
}

