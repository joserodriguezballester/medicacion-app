package com.example.prueba3.ui.components


import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.prueba3.ui.components.drawer.DrawerContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawerContent(
    onDestinoSeleccionado: (String) -> Unit,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController
) {
    DrawerContent { destino ->
        coroutineScope.launch {
            drawerState.close()
            navController.navigate(destino) {
                popUpTo(0)
            }
        }
    }
}
