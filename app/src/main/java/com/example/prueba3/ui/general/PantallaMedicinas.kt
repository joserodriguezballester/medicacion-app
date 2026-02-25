package com.example.prueba3.ui.general

import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.model.GrupoCompra

sealed class PantallaMedicinas {
    data class BD(val lista: List<Medicina>) : PantallaMedicinas()
    data class Renovar(val lista: List<Medicina>) : PantallaMedicinas()
    data class Tratamiento(val lista: List<Medicina>) : PantallaMedicinas()
    data class Compra(val grupo: GrupoCompra) : PantallaMedicinas()
    object General : PantallaMedicinas()
}