package com.example.prueba3.ui.screens

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.prueba3.R
// import com.example.prueba3.R

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Usamos LaunchedEffect para esperar unos segundos
    LaunchedEffect(Unit) {
        delay(1000) // 2 segundos
        onTimeout()
    }

    // Diseño del splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash2),
            contentDescription = "App Icon",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight
            //       .size(600.dp)

        )
    }
}
