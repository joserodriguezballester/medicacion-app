package com.example.prueba3.ui.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.domain.MedicinaValidator
import java.time.LocalDate

/**
 * Representa el estado UI editable para una medicina durante creación o edición.
 * Se utiliza en formularios dentro de pantallas composable.
 */
data class FormularioMedicina(
    val name: MutableState<String> = mutableStateOf(""),
    val principioActivo: MutableState<String> = mutableStateOf(""),
    val stock: MutableState<String> = mutableStateOf(""),
    val dosis: MutableState<String> = mutableStateOf(""),
    val unidadesCaja: MutableState<String> = mutableStateOf(""),
    val fechaInicio: MutableState<LocalDate?> = mutableStateOf(null),
    val fechaFin: MutableState<LocalDate?> = mutableStateOf(null),
    val fechaStock: MutableState<LocalDate?> = mutableStateOf(null)
)

fun FormularioMedicina.validar(): Map<String, String?> {
    return MedicinaValidator.validarMedicina(
        name.value,
        principioActivo.value,
        stock.value,
        dosis.value,
        unidadesCaja.value,
        fechaInicio.value ?: LocalDate.now(),
        fechaFin.value ?: LocalDate.now().plusYears(1),
        fechaStock.value ?: LocalDate.now()
    )
}

fun FormularioMedicina.toMedicina(base: Medicina? = null): Medicina {
    return base?.copy(
        name = name.value,
        principioActivo = principioActivo.value,
        stock = stock.value.toIntOrNull() ?: 0,
        dosis = dosis.value,
        unidadesCaja = unidadesCaja.value.toIntOrNull() ?: 0,
        fechaInicioTratamiento = fechaInicio.value ?: LocalDate.now(),
        fechaFinTratamiento = fechaFin.value ?: LocalDate.now().plusYears(1),
        fechaStock = fechaStock.value ?: LocalDate.now()
    ) ?: Medicina(
        id = 0,
        name = name.value,
        principioActivo = principioActivo.value,
        stock = stock.value.toIntOrNull() ?: 0,
        dosis = dosis.value,
        unidadesCaja = unidadesCaja.value.toIntOrNull() ?: 0,
        fechaInicioTratamiento = fechaInicio.value ?: LocalDate.now(),
        fechaFinTratamiento = fechaFin.value ?: LocalDate.now().plusYears(1),
        fechaStock = fechaStock.value ?: LocalDate.now()
    )
}