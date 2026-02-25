package com.example.prueba3.navigation

import android.R.attr.iconTint
import android.app.ProgressDialog.show
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prueba3.viewmodel.MedicinaViewModel

data class ScreenConfig(
    val showCompactToggle: Boolean = false,
    val showSave: Boolean = false,
    val showBackButton: Boolean = false,
    val saveAction: (() -> Unit)? = null,
    val iconSize: Dp = 32.dp,
    val iconTint: Color = Color.Unspecified
)

fun getScreenConfig(
    currentRoute: String?,
    modoCompacto: Boolean,
    viewModel: MedicinaViewModel,
    navController: NavHostController
): ScreenConfig {
    return when {
        currentRoute == Screen.Inventario.route -> ScreenConfig(
            showCompactToggle = true,
            saveAction = { viewModel.toggleModoCompacto() }
        )

        currentRoute == Screen.AddMedicina.route -> ScreenConfig(
            showSave = true,
            saveAction = { viewModel.validarYGuardar(navController) }
        )

        currentRoute?.startsWith("edicion/") == true -> ScreenConfig(
            showSave = true,
            saveAction = { viewModel.validarYActualizar(navController) },
            iconSize = 40.dp,
            iconTint = Color.White
        )

        currentRoute == Screen.Todos.route -> ScreenConfig(
            showBackButton = viewModel.mostrarBotonVolver,
            showCompactToggle = !viewModel.mostrarBotonVolver,
            saveAction = {
                if (viewModel.mostrarBotonVolver) {
                    viewModel.volverAPantallaGeneral()
                    viewModel.desactivarVolver()
                } else {
                    viewModel.toggleModoCompactoGeneralScreen()
                }
            },
            iconTint = Color.White
        )

        else -> ScreenConfig()
    }
}