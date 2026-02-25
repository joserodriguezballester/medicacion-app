package com.example.prueba3.ui.components.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import com.example.prueba3.model.DrawerOption
import com.example.prueba3.navigation.Screen

object DrawerOptions {
    val items = listOf(
        DrawerOption("Home", Icons.Default.Home, Screen.Home.route),
        DrawerOption("Inventario", Icons.Default.Edit, Screen.Inventario.route),
        DrawerOption("Tratamiento", Icons.Default.Info, Screen.Tratamiento.route),
        DrawerOption("Modificar Tratamiento", Icons.Default.Edit, Screen.Modificar.route),
        DrawerOption("Nuevas Medicinas", Icons.Default.Build, Screen.AddMedicina.route),
        DrawerOption("Editar Medicinas", Icons.Default.ArrowDropDown, Screen.Medicinas.route),
        DrawerOption("Todos los medicamentos", Icons.Default.CheckCircle, Screen.Todos.route)
    )
}