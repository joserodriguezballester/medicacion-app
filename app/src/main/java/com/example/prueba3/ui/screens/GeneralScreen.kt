package com.example.prueba3.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.ui.cards.AllInfoCard
import com.example.prueba3.ui.cards.CompactInfoCard
import com.example.prueba3.ui.general.MostrarListaBD
import com.example.prueba3.ui.general.MostrarListaCompra
import com.example.prueba3.ui.general.MostrarListaRenovar
import com.example.prueba3.ui.general.MostrarListaTratamiento
import com.example.prueba3.ui.general.PantallaMedicinas
import com.example.prueba3.ui.theme.ColorEnFarmacia
import com.example.prueba3.ui.theme.CustomTypography
import com.example.prueba3.viewmodel.MedicinaViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeneralScreen(viewModel: MedicinaViewModel, innerPadding: PaddingValues) {
    val context = LocalContext.current

    val expandidoFarmacia = rememberSaveable { mutableStateOf(true) }
    val expandidoActivas = rememberSaveable { mutableStateOf(true) }
    val expandidoInactivas = rememberSaveable { mutableStateOf(true) }

    val estado by viewModel.estadoMedicinas.collectAsState()
    val modoCompacto by viewModel.modoCompactoGeneralScreen.collectAsState()

    val pantalla by viewModel.pantallaActual.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        when (pantalla) {
            is PantallaMedicinas.BD -> {
                viewModel.activarVolver()
                val lista = (pantalla as PantallaMedicinas.BD).lista
                item {
                    MostrarListaBD(lista, viewModel, context)
                }
            }

            is PantallaMedicinas.Renovar -> {
                viewModel.activarVolver()
                val lista = (pantalla as PantallaMedicinas.Renovar).lista
                item {
                    MostrarListaRenovar(lista, viewModel, context)
                }
            }

            is PantallaMedicinas.Tratamiento -> {
                viewModel.activarVolver()
                val lista = (pantalla as PantallaMedicinas.Tratamiento).lista
                item {
                    MostrarListaTratamiento(lista, viewModel, context)
                }
            }

            is PantallaMedicinas.Compra -> {
                viewModel.activarVolver()
                val lista = (pantalla as PantallaMedicinas.Compra).grupo
                item {
                    MostrarListaCompra(lista, viewModel, context, innerPadding)
                }
            }

            PantallaMedicinas.General -> {
                viewModel.desactivarVolver()

                GrupoMedicinas(
                    "⚠️ Medicinas en Farmacia",
                    estado.enFarmacia,
                    modoCompacto,
                    ColorEnFarmacia,
                    true,
                    expandido = expandidoFarmacia.value,
                    onToggleExpandido = {
                        expandidoFarmacia.value = !expandidoFarmacia.value
                    }
                )
                GrupoMedicinas(
                    "💊 Medicinas Activas",
                    estado.activas,
                    modoCompacto, Color.White,
                    false,
                    expandidoActivas.value,
                    onToggleExpandido = {
                        expandidoActivas.value = !expandidoActivas.value
                    }
                )
                GrupoMedicinas(
                    "🌑 Medicinas Inactivas",
                    estado.inactivas,
                    modoCompacto,
                    Color.LightGray,
                    false,
                    expandido = expandidoInactivas.value,
                    onToggleExpandido = {
                        expandidoInactivas.value = !expandidoInactivas.value
                    }
                )
            }
        }
    }
}

@Composable
private fun EncabezadoFilasCompacto() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Nombre",
            style = CustomTypography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            "Fecha Receta",
            style = CustomTypography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            "Fecha Farmacia",
            style = CustomTypography.labelMedium,
            modifier = Modifier.weight(1f)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.GrupoMedicinas(
    titulo: String,
    medicinas: List<Medicina>,
    modoCompacto: Boolean,
    colorFondo: Color,
    enFarmacia: Boolean = false,
    expandido: Boolean,
    onToggleExpandido: () -> Unit
) {
    if (medicinas.isNotEmpty()) {
        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(titulo, style = CustomTypography.titleMedium)
                    IconButton(onClick = { onToggleExpandido() }) {
                        Icon(
                            imageVector = if (expandido) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (expandido) "Colapsar" else "Expandir"
                        )
                    }
                }
                if (modoCompacto) {
                    EncabezadoFilasCompacto()
                }
            }
        }
        if (expandido) {
// para poner un borde a las medicinas que estan en farmacia
            if (enFarmacia) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color.Red,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        medicinas.forEach { medicina ->
                            if (modoCompacto) {
                                CompactInfoCard(medicina, ColorEnFarmacia, enFarmacia = true)
                            } else {
                                AllInfoCard(medicina = medicina, colorFondo = ColorEnFarmacia)
                            }
                        }
                    }
                }
            } else {
                items(medicinas) { medicina ->
                    val fondo = colorFondo
                    if (modoCompacto) {
                        CompactInfoCard(medicina, fondo, enFarmacia)
                    } else {
                        AllInfoCard(medicina, colorFondo = fondo)
                    }
                }
            }
        }
    }
}

