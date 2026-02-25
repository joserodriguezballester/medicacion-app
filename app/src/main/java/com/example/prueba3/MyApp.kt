package com.example.prueba3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prueba3.navigation.getCurrentScreen
import com.example.prueba3.ui.components.AppDrawerContent
import com.example.prueba3.ui.components.PantallaPrincipal
import com.example.prueba3.viewmodel.MedicinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(paddingValues: PaddingValues, viewModel: MedicinaViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isCompact by viewModel.modoCompacto.collectAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screenActual = getCurrentScreen(currentRoute)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                Modifier
                    .width(280.dp) // Ajusta el ancho aquí
                    .fillMaxHeight()
            )
            {
                AppDrawerContent(
                    onDestinoSeleccionado = {}, // si necesitas manejar algo extra
                    drawerState = drawerState,
                    coroutineScope = coroutineScope,
                    navController = navController
                )
            }
        }
    ) {
        PantallaPrincipal(
            navController = navController,
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            viewModel = viewModel,
            currentRoute = currentRoute,
            screenActual = screenActual,
            modoCompacto = isCompact,
            paddingValues = paddingValues
        )
    }
}



