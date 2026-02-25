package com.example.prueba3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Medication
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector



sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val color: Color
) {
        object Home : Screen(
            route = "home",
            title = "Inicio 🏠",
            icon = Icons.Default.Home,
            color = Color(0xFF1976D2) // Azul clásico
        )

        object Inventario : Screen(
            route = "inventario",
            title = "Inventario 🧪",
            icon = Icons.Default.Inventory2,
            color = Color(0xFFD32F2F) // Rojo intenso
        )

        object Tratamiento : Screen(
            route = "tratamiento",
            title = "Tratamiento 💊",
            icon = Icons.Default.LocalHospital,
            color = Color(0xFF388E3C) // Verde medicina
        )

        object Modificar : Screen(
            route = "modificar",
            title = "Modificar ✏️",
            icon = Icons.Default.Edit,
            color = Color(0xFFFFA000) // Ámbar fuerte
        )

        object EditMedicinas : Screen(
            route = "editmedicinas",
            title = "Editar Medicinas",
            icon = Icons.Default.EditNote,
            color = Color(0xFF7B1FA2) // Púrpura profesional
        )

        object Medicinas : Screen(
            route = "medicinas",
            title = "Editar Medicinas",
            icon = Icons.Default.Medication,
            color = Color(0xFF00897B) // Verde-azulado quirúrgico
        )

        object AddMedicina : Screen(
            route = "addMedicina",
            title = "Nueva Medicina ",
            icon = Icons.Default.AddCircle,
            color = Color(0xFF0288D1) // Azul acción
        )

        object Edicion : Screen(
            route = "edicion/{medicinaId}",
            title = "Editar Medicina",
            icon = Icons.Default.Build,
            color = Color(0xFF5D4037) // Marrón técnico
        ) {
            fun createRoute(medicinaId: Int) = "edicion/$medicinaId"
        }

        object Todos : Screen(
            route = "todos",
            title = "Vista General 🔍",
            icon = Icons.Default.ListAlt,
            color = Color(0xFF455A64) // Gris profesional
        )
}