package com.example.prueba3.model

import com.example.prueba3.data.local.entity.Medicina

data class EstadoMedicinas(
    val activas: List<Medicina>,
    val inactivas: List<Medicina>,
    val enFarmacia: List<Medicina>
)
