package com.example.prueba3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.temporal.ChronoUnit


/**
 * Representa una medicina almacenada en la base de datos.
 */
@Entity(tableName = Medicina.TABLE_NAME)
data class Medicina(
    /**
     * ID autogenerado para cada medicina.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "dosis")
    var dosis: String,

    @ColumnInfo(name = "unidadescaja")
    var unidadesCaja: Int,

    @ColumnInfo(name = "stock")
    var stock: Int,

    @ColumnInfo(name = "principioactivo")
    val principioActivo: String,

    @ColumnInfo(name = "fecha_inicio_tratamiento")
    var fechaInicioTratamiento: LocalDate? = null,

    @ColumnInfo(name = "fecha_fin_tratamiento")
    var fechaFinTratamiento: LocalDate? = null,

    @ColumnInfo(name = "fecha_stock")
    var fechaStock: LocalDate? = null
) {
    companion object {
        const val TABLE_NAME = "medicina_data_table"
    }

    val consumoDiario: Double
        get() = dosis.mapNotNull { it.digitToIntOrNull() }
            .sumOf { if (it == 5) 0.5 else it.toDouble() }

    val diasDisponibles: Int
        get() = (if (consumoDiario > 0) (stockActualizado / consumoDiario).toInt() else 0) as Int

    val fechaFinStock: LocalDate
        get() = LocalDate.now().plusDays(diasDisponibles.toLong())

    val semanasPendientes: Int
        get() = diasDisponibles / 7

    val proximaFechaPedido: LocalDate?
        get() {
            val inicio = fechaInicioTratamiento ?: return null
            if (consumoDiario <= 0 || unidadesCaja <= 0) return null

            val hoy = LocalDate.now()
            val duracionCaja = (unidadesCaja / consumoDiario).toLong()

            var fechaActual = inicio
            var ultimaFechaAnterior: LocalDate? = null

            while (true) {
                val fechaAgotamiento = fechaActual.plusDays(duracionCaja)
           //     val fechaPedido = fechaAgotamiento.minusDays(14)
                val fechaPedido = fechaAgotamiento.minusDays(0)

                if (fechaFinTratamiento != null && fechaPedido.isAfter(fechaFinTratamiento)) break

                if (fechaPedido.isAfter(hoy)) {
                    return fechaPedido // más cercana futura, terminamos aquí
                }

                // Fecha anterior, pero solo si no se ha actualizado el stock desde entonces
                if (fechaStock == null || fechaStock!!.isBefore(fechaPedido)) {
                    ultimaFechaAnterior = fechaPedido
                }

                fechaActual = fechaAgotamiento
            }

            return ultimaFechaAnterior
        }
    val fechaEnFarmacia: LocalDate?
        get() {
            return  proximaFechaPedido?.minusDays(14)
        }


    val stockActualizado: Int
        get() {
            val diasPasados = ChronoUnit.DAYS.between(fechaStock, LocalDate.now())
            val nuevoStock = stock - (consumoDiario * diasPasados)
            return nuevoStock.coerceAtLeast(0.0).toInt()
        }



}