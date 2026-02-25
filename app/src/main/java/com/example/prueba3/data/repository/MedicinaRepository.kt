package com.example.prueba3.data.repository

import com.example.prueba3.data.local.dao.MedicinaDAO
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.utils.pasarAFechaddMMyyyy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class MedicinaRepository(private val medicinaDAO: MedicinaDAO) {

    val medicinas = medicinaDAO.getAllMedicinas()

    suspend fun cargaInicialSiNecesaria() {
        crearMedicinasIniciales().forEach { insert(it) } //Llena la BD
    }




    suspend fun insert(medicina: Medicina): Long {
        return medicinaDAO.insertMedicina(medicina)
    }

    suspend fun update(medicina: Medicina): Int {
        return medicinaDAO.updateMedicina(medicina)
    }

    suspend fun delete(medicina: Medicina): Int {
        return medicinaDAO.deleteMedicina(medicina)
    }

    suspend fun deleteAll(): Int {
        return medicinaDAO.deleteAll()
    }

    fun searchPorNombre(nombre: String): Flow<List<Medicina>> {
        return medicinaDAO.searchMedicinaPorNombre(nombre)
    }

    suspend fun getById(id: Int): Medicina? {
        return medicinaDAO.getMedicinaById(id)
    }

    suspend fun getCantidad(): Int {
        return medicinaDAO.getCantidadMedicinas()
    }







}