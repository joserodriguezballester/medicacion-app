package com.example.prueba3.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.prueba3.navigation.MainContent
import com.example.prueba3.navigation.Screen
import com.example.prueba3.ui.general.BottomActionButtonsCompact
import com.example.prueba3.ui.theme.BackgroundWrapper
import com.example.prueba3.viewmodel.MedicinaViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    viewModel: MedicinaViewModel,
    currentRoute: String?,
    screenActual: Screen?,
    modoCompacto: Boolean,
    paddingValues: PaddingValues
) {

    Scaffold(
        topBar = {
            AppTopBar(
                screenActual,
                coroutineScope,
                drawerState,
                currentRoute,
                viewModel,
                modoCompacto,
                navController
            )
        },
        bottomBar = {
            if (currentRoute == Screen.Todos.route) {
                BottomActionButtonsCompact(
                    onEnviarCompra = { viewModel.enviarCompraYActualizar() },
                    onEnviarTratamiento = { viewModel.enviarTratamientoYActualizar() },
                    onEnviarRenovar = { viewModel.enviarRenovarYActualizar() },
                    onEnviarBD = { viewModel.enviarBDYActualizar() }
                )
            }
        },
        floatingActionButton = {
            val compartirMensaje = viewModel.mensajeCompartir
            if (viewModel.mostrarBotonVolver && compartirMensaje.isNotBlank()) {
                WhatsAppFab(
                    viewModel = viewModel,
                    context = LocalContext.current,
                    generarMensaje = { compartirMensaje })
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        BackgroundWrapper {
            MainContent(
                navController, innerPadding, viewModel,
                actions = {}
            )
        }
    }
}

