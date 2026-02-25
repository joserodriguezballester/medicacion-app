package com.example.prueba3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.navigation.Screen
import com.example.prueba3.ui.cards.MensajeToastListener
import com.example.prueba3.ui.theme.colorDeFondoPorDosis


import com.example.prueba3.utils.formatearFecha
import com.example.prueba3.viewmodel.MedicinaViewModel
import kotlinx.coroutines.delay

@Composable
fun ListadoMedicinasTratamientoScreen(
    viewModel: MedicinaViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val listaMedicinas by viewModel.medicinas.collectAsState()
    val medicinasActivas = remember(listaMedicinas) {
        listaMedicinas.filter { it.dosis != "0000" }
    }
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
////////// Aqui poner la lista que se desee, todas o solo tto ///////////
        itemsIndexed(listaMedicinas) { index, medicina ->
            val colorFondo = colorDeFondoPorDosis(medicina.dosis)
            var visible by remember { mutableStateOf(false) }

            // Delay animación progresiva por index
            LaunchedEffect(Unit) {
                delay(150L * index)
                visible = true
            }

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(tween(500)),
            ) {
                MedicinaExpandableCard2(
                    medicina,
                    onEdit = {
                        navController.navigate(Screen.Edicion.createRoute(medicina.id))
                    },
                    onDeleteConfirmed = { viewModel.deleteMedicina(medicina) }
                )
            }
        }
    }
}


@Composable
fun MedicinaExpandableCard2(
    medicina: Medicina,
    onEdit: () -> Unit = {},
    onDeleteConfirmed: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(12.dp)
            .animateContentSize(animationSpec = tween(400)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color(0xFF00796B),
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE0F2F1), CircleShape)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = medicina.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = medicina.principioActivo,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "📦 Unidades por caja: ${medicina.unidadesCaja}")
                Text(text = "📦 Stock: ${medicina.stockActualizado} (desde ${medicina.fechaStock!!.formatearFecha()})")
                Text(text = "🕒 Dosis : ${medicina.dosis}")
                Text(text = "📅 Desde:  ${medicina.fechaInicioTratamiento!!.formatearFecha()} hasta  ${medicina.fechaFinTratamiento!!.formatearFecha()}")
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onEdit) {
                        println("Navegando a editar ${medicina.id}")
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { showDialog = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }

            // Diálogo de confirmación
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmar eliminación") },
               //     text = { Text("¿Estás seguro de que quieres eliminar esta medicina? Esta acción no se puede deshacer.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            onDeleteConfirmed()
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

