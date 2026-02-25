package com.example.prueba3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prueba3.navigation.Screen
import com.example.prueba3.navigation.getScreenConfig
import com.example.prueba3.viewmodel.MedicinaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopBar(
    screenActual: Screen?,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    currentRoute: String?,
    viewModel: MedicinaViewModel,
    modoCompacto: Boolean,
    navController: NavHostController
) {

    TopAppBar(
        title = { Text(screenActual?.title ?: "Mi App") },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { drawerState.open() }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            val config = getScreenConfig(currentRoute, modoCompacto, viewModel, navController)

            if (config.showCompactToggle) {
                IconButton(onClick = { config.saveAction?.invoke() }) {
                    Icon(
                        imageVector = if (modoCompacto) Icons.Default.ViewList else Icons.Default.TableChart,
                        contentDescription = if (modoCompacto) "Vista Lista" else "Vista Compacta",
                        modifier = Modifier.size(config.iconSize),
                        tint = config.iconTint
                    )
                }
            }
            if (config.showSave) {
                IconButton(onClick = { config.saveAction?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(config.iconSize),
                        tint = config.iconTint
                    )
                }
            }
            if (config.showBackButton){
                IconButton(onClick = { config.saveAction?.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        modifier = Modifier.size(config.iconSize),
                        tint = config.iconTint
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor =  Color.Transparent,
       //     containerColor = Color(0xFF6200EE) ,// ✅ Color sólido
    //       containerColor =  MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
               .fillMaxWidth()
            .graphicsLayer {
                alpha = 0.8f // Transparencia
                shadowElevation = 16f
                shape = RoundedCornerShape(12.dp)
                clip = true
            }
        //    .blur(32.dp) // Desenfoque del fondo
            .background(
                color = Color.White.copy(alpha = 0.25f), // Capa semitransparente
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp) // Espacio para que respire
       )
}



