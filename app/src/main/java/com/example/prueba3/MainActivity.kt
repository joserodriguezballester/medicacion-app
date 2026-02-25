package com.example.prueba3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.prueba3.data.local.database.MedicinaDatabase
import com.example.prueba3.data.repository.MedicinaRepository
import com.example.prueba3.ui.theme.BackgroundWrapper
import com.example.prueba3.ui.theme.Prueba3Theme
import com.example.prueba3.viewmodel.MedicinaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

// Crear dependencias manualmente
        val db = MedicinaDatabase.getInstance(applicationContext)
        val repo = MedicinaRepository(db.medicinaDAO)
        val viewModel = MedicinaViewModel(repo, applicationContext)

        setContent {
            Prueba3Theme {
                Scaffold(
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                ) {it ->
                    MyApp(it,viewModel)
                }
            }
        }
    }
}
