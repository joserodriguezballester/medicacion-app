package com.example.prueba3.domain

import java.time.LocalDate

object MedicinaValidator {

    fun validarDosis(dosis: String): String? {
        return if (!dosis.matches(Regex("^\\d{4}$"))) {
            "La dosis debe ser un número de 4 cifras"
        } else null
    }

    fun validarMedicina(
        name: String,
        principioActivo: String,
        stock: String,
        dosis: String,
        unidadesCaja: String,
        fechaInicio: LocalDate?,
        fechaFin: LocalDate?,
        fechaStock: LocalDate?
    ): Map<String, String?> {
        val errores = mutableMapOf<String, String?>()
        if (name.isBlank()) errores["name"] ="El nombre no puede estar vacío."
        if (principioActivo.isBlank()) errores["principioActivo"] ="El principio activo no puede estar vacío."
        if (stock.toIntOrNull() == null || stock.toInt() < 0) errores["stock"] ="El stock debe ser un número válido."
        validarDosis(dosis)?.let { errores["dosis"] = it }
        if (unidadesCaja.toIntOrNull() == null || unidadesCaja.toInt() <= 0) errores["unidadesCaja"] = "Las unidades por caja deben ser mayores a 0."
        if (fechaInicio == null) errores["fechaInicio"] = "La fecha de inicio del tratamiento es obligatoria."
        if (fechaFin == null) errores["fechaFin"]= "La fecha de fin del tratamiento es obligatoria."
        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) errores["fechaFin"]= "La fecha de fin no puede ser anterior a la de inicio."
        if (fechaStock == null) errores["fechaStock"] = "La fecha de stock es obligatoria."

        return errores
    }
}