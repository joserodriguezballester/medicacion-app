package com.example.prueba3.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.prueba3.ui.screens.AddMedicinaScreen
import com.example.prueba3.ui.screens.GeneralScreen
import com.example.prueba3.ui.screens.EditMedicinaScreen
import com.example.prueba3.ui.screens.HomeScreen
import com.example.prueba3.ui.screens.InventarioScreen
import com.example.prueba3.ui.screens.ListadoMedicinasTratamientoScreen
import com.example.prueba3.ui.screens.ModificarTratamientoScreen
import com.example.prueba3.ui.screens.SplashScreen

import com.example.prueba3.ui.screens.TratamientoScreen
import com.example.prueba3.viewmodel.MedicinaViewModel


@Composable
fun MainContent(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: MedicinaViewModel,
    actions: @Composable () -> Unit
) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                navController.navigate(Screen.Home.route) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen(innerPadding) { route ->
                navController.navigate(route)
            }
        }
        composable(Screen.Inventario.route) {
            InventarioScreen(viewModel, innerPadding)
        }
        composable(Screen.Tratamiento.route) {
            TratamientoScreen(viewModel, innerPadding)
        }
        composable(Screen.Modificar.route) {
            ModificarTratamientoScreen(viewModel, innerPadding,navController)
        }
        composable(Screen.AddMedicina.route) {
            AddMedicinaScreen(
                viewModel = viewModel,
                innerPadding = innerPadding,
                navController = navController
            )
        }
        composable(Screen.EditMedicinas.route) {
            EditMedicinaScreen(viewModel,
                innerPadding,
                382,
                navController)
        }
        composable(Screen.Medicinas.route) {
            ListadoMedicinasTratamientoScreen(
                viewModel,
                innerPadding,
                navController)
        }
        composable(Screen.Edicion.route,
            arguments = listOf(navArgument("medicinaId") {
                type = NavType.IntType }))
        {backStackEntry ->
            val medicinaId = backStackEntry.arguments?.getInt("medicinaId") ?: return@composable
            EditMedicinaScreen(viewModel, innerPadding,medicinaId,navController)
        }

        composable(Screen.Todos.route) {
            GeneralScreen(
                viewModel,
                innerPadding
                )
        }
    }
}

