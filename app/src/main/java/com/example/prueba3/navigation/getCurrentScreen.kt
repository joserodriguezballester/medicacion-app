package com.example.prueba3.navigation

fun getCurrentScreen(currentRoute: String?): Screen? {
    return when {
        currentRoute == null -> null
        currentRoute == Screen.Edicion.route || currentRoute.startsWith("edicion/") -> Screen.Edicion
        else -> listOf(
            Screen.Home,
            Screen.Inventario,
            Screen.Tratamiento,
            Screen.Modificar,
            Screen.EditMedicinas,
            Screen.Medicinas,
            Screen.AddMedicina,
            Screen.Todos
        ).firstOrNull { it.route == currentRoute }
    }
}