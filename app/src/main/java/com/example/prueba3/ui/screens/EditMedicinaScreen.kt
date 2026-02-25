package com.example.prueba3.ui.screens

import android.R.attr.name
import androidx.compose.material.icons.filled.DateRange
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prueba3.domain.MedicinaValidator
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.viewmodel.MedicinaViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar

@Composable
fun EditMedicinaScreen(
    viewModel: MedicinaViewModel,
    innerPadding: PaddingValues,
    medicinaId: Int,
    navController: NavHostController
) {

    val errores = remember { mutableStateMapOf<String, String?>() }
    val medicina by viewModel.medicina.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    MensajeToastListener(
        mensaje = mensaje,
        onMensajeMostrado = { viewModel.limpiarMensaje() }
    )
    // Cargar la medicina al iniciar la pantalla
    LaunchedEffect(medicinaId) {
        viewModel.loadMedicinaById(medicinaId)
    }
    LaunchedEffect(medicina) {
        medicina?.let {
            viewModel.formulario.name.value = it.name
            viewModel.formulario.principioActivo.value = it.principioActivo
            viewModel.formulario.stock.value = it.stockActualizado.toString()
            viewModel.formulario.dosis.value = it.dosis
            viewModel.formulario.unidadesCaja.value = it.unidadesCaja.toString()
            viewModel.formulario.fechaInicio.value = it.fechaInicioTratamiento
            viewModel.formulario.fechaFin.value = it.fechaFinTratamiento
            viewModel.formulario.fechaStock.value = it.fechaStock
        }
    }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        medicina?.let {
            MedicinaFormFields(
                formulario = viewModel.formulario,
                errores = errores
            )

            Spacer(modifier = Modifier.height(16.dp))
        } ?: CircularProgressIndicator()
    }
}

