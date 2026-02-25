package com.example.prueba3.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.domain.MedicinaValidator
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.viewmodel.MedicinaViewModel
import java.time.LocalDate

@Composable
fun AddMedicinaScreen(
    viewModel: MedicinaViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val mensaje by viewModel.mensaje.collectAsState()

    // 🎯 Toast para mostrar el mensaje de error o confirmación
    MensajeToastListener(
        mensaje = mensaje,
        onMensajeMostrado = { viewModel.limpiarMensaje() }
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp, 4.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MedicinaFormFields(
            formulario = viewModel.formulario,
            errores = viewModel.errores
        )
    }
}
