package com.example.prueba3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prueba3.R
import com.example.prueba3.ui.components.drawer.DrawerOptions
import com.example.prueba3.ui.components.drawer.DrawerCard

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top=40.dp)
        // Imagen de fondo
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
              ,
            contentScale = ContentScale.Crop // Ajusta la imagen al tamaño del contenedor
        )
        // Overlay semitransparente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f)) // Puedes ajustar el alpha
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(DrawerOptions.items) { item ->
                DrawerCard(
                    text = item.label,
                    onClick = { onNavigate(item.route) }
                )
            }
        }
    }

}