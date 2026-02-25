package com.example.prueba3.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.prueba3.ui.model.FormularioMedicina
import com.example.prueba3.ui.theme.borde2dp
import com.example.prueba3.ui.theme.coloresCampoTexto
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MedicinaFormFields(
    formulario: FormularioMedicina,
    errores: Map<String, String?> // ← AÑADIDO
) {
    // 📛 Nombre
    CampoTextoConError(
        value = formulario.name.value,
        onValueChange = { formulario.name.value = it },
        label = "Nombre",
        error = errores["name"]
    )

// 💊 Principio activo
    CampoTextoConError(
        value = formulario.principioActivo.value,
        onValueChange = { formulario.principioActivo.value = it },
        label = "Principio Activo",
        error = errores["principioActivo"]
    )

// 📦 Stock
    CampoNumericoConError(
        value = formulario.stock.value,
        onValueChange = { formulario.stock.value = it },
        label = "Stock",
        error = errores["stock"]
    )

// 🕒 Dosis diaria
    CampoNumericoConError(
        value = formulario.dosis.value,
        onValueChange = { formulario.dosis.value = it },
        label = "Dosis Diaria",
        error = errores["dosis"]
    )

// 📦 Unidades por caja
    CampoNumericoConError(
        value = formulario.unidadesCaja.value,
        onValueChange = { formulario.unidadesCaja.value = it },
        label = "Unidades por Caja",
        error = errores["unidadesCaja"]
    )

    Spacer(modifier = Modifier.height(8.dp))

// 🕒 Fecha Inicio
    InlineDatePickerField2(
        label = "Fecha Inicio Tratamiento",
        date = formulario.fechaInicio.value,
        onDateSelected = { formulario.fechaInicio.value = it },
        isError = errores["fechaInicio"] != null
    )

    Spacer(modifier = Modifier.height(8.dp))

// 🕒 Fecha Fin
    InlineDatePickerField2(
        label = "Fecha Fin Tratamiento",
        date = formulario.fechaFin.value,
        onDateSelected = { formulario.fechaFin.value = it },
        isError = errores["fechaFin"] != null
    )

    Spacer(modifier = Modifier.height(8.dp))

// 🕒 Fecha Stock
    InlineDatePickerField2(
        label = "Fecha de Stock",
        date = formulario.fechaStock.value,
        onDateSelected = { formulario.fechaStock.value = it },
        isError = errores["fechaStock"] != null
    )

    Spacer(modifier = Modifier.height(8.dp))


}

@Composable
fun CampoTextoConError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val isError = error != null

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = coloresCampoTexto(),
        label = { Text(label) },
        isError = isError,
        supportingText = if (isError) {
            { Text(error, color = MaterialTheme.colorScheme.error) }
        } else null,
        modifier = modifier
        // .border(2.dp, Color(0xFF00FFFF), RoundedCornerShape(12.dp))
    )
}


@Composable
fun CampoNumericoConError(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val isError = error != null
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        colors = coloresCampoTexto(),
        label = { Text(label) },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = if (isError) {
            { Text(error!!, color = MaterialTheme.colorScheme.error) }
        } else null,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<Int>,
    selected: Int,
    onSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected.toString(),
            onValueChange = {},
            colors = coloresCampoTexto(),
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .width(100.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun InlineDatePickerField2(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    isError: Boolean = false // 👈 nuevo parámetro
) {
    val currentDate = date ?: LocalDate.now()
    println("fecha $currentDate")
    var selectedDay by remember(date) { mutableStateOf(currentDate.dayOfMonth) }
    var selectedMonth by remember(date) { mutableStateOf(currentDate.monthValue) }
    var selectedYear by remember(date) { mutableStateOf(currentDate.year) }


    val days = (1..31).toList()
    val months = (1..12).toList()
    val fullYears = (2000..2099).toList()
    val yearPairs = fullYears.map { year -> Pair(year % 100, year) }
    val selectedDisplayYear = selectedYear % 100

    fun updateDate(day: Int = selectedDay, month: Int = selectedMonth, year: Int = selectedYear) {
        val safeDay = day.coerceAtMost(YearMonth.of(year, month).lengthOfMonth())
        onDateSelected(LocalDate.of(year, month, safeDay))
    }

    val borderColor =
        if (isError) MaterialTheme.colorScheme.error else
        //MaterialTheme.colorScheme.outline
            Color(0xFF00FFFF)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                borderColor,
                shape = MaterialTheme.shapes.medium
            )

            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                label,
                //   style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f)
            )
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Fecha inválida",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DropdownSelector("Día", days, selectedDay) {
                selectedDay = it
                updateDate()
            }
            DropdownSelector("Mes", months, selectedMonth) {
                selectedMonth = it
                updateDate()
            }
            DropdownSelector("Año", yearPairs.map { it.first }, selectedDisplayYear) { visualYear ->
                selectedYear = yearPairs.first { it.first == visualYear }.second
                updateDate()
            }
        }
    }
}


@Composable
fun InlineDatePickerField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentDate = date ?: LocalDate.now()

    var selectedDay by remember { mutableStateOf(currentDate.dayOfMonth) }
    var selectedMonth by remember { mutableStateOf(currentDate.monthValue) }
    var selectedYear by remember { mutableStateOf(currentDate.year) }

    val days = (1..31).toList()
    val months = (1..12).toList()
    val fullYears = (2000..2099).toList() // años reales
    val yearPairs = fullYears.map { year ->
        Pair(year % 100, year) // (visual, real) }
    }
    val selectedDisplayYear = selectedYear % 100

    // ✅ Declaramos la función aquí, dentro del Composable pero fuera de las lambdas
    fun updateDate(day: Int = selectedDay, month: Int = selectedMonth, year: Int = selectedYear) {
        val safeDay = day.coerceAtMost(YearMonth.of(year, month).lengthOfMonth())
        onDateSelected(LocalDate.of(year, month, safeDay))
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.labelMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DropdownSelector(
                label = "Día",
                options = days,
                selected = selectedDay,
                onSelected = {
                    selectedDay = it
                    updateDate()
                }
            )
            DropdownSelector(
                label = "Mes",
                options = months,
                selected = selectedMonth,
                onSelected = {
                    selectedMonth = it
                    updateDate()
                }
            )
            DropdownSelector(
                label = "Año",
                options = yearPairs.map { it.first },
                selected = selectedYear % 100,
                onSelected = { visualYear ->
                    selectedYear = yearPairs.first { it.first == visualYear }.second
                    updateDate()
                }
            )
        }
    }
}
