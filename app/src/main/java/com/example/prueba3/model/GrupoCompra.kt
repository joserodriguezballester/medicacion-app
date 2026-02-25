package com.example.prueba3.model

import com.example.prueba3.data.local.entity.Medicina

data class GrupoCompra(
    val enFarmacia: List<Medicina>,
    val noEnFarmacia: List<Medicina>
)
