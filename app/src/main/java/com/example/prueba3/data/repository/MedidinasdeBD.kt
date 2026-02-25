package com.example.prueba3.data.repository

import com.example.prueba3.data.local.entity.Medicina
import java.time.LocalDate

val listaMedicinasB = listOf(

    Medicina(
        name = "Quetiapina",
        dosis = "0001",
        unidadesCaja = 60,
        stock = 91,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-07"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Rexer",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 39,
        principioActivo = "Mirtazapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-08"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Dormodor ",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 46,
        principioActivo = "Flurazepam",
        fechaInicioTratamiento = LocalDate.parse("2025-07-10"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Diazepam",
        dosis = "0002",
        unidadesCaja = 40,
        stock = 44,
        principioActivo = "--",
        fechaInicioTratamiento = LocalDate.parse("2025-07-20"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Largactil 25",
        dosis = "0120",
        unidadesCaja = 50,
        stock = 79,
        principioActivo = "Clorpromazina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-25"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Venlafaxina 300",
        dosis = "1000",
        unidadesCaja = 430,
        stock = 26,
        principioActivo = "Venlafaxina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-18"),
        fechaFinTratamiento = LocalDate.parse("2025-08-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Ebastel",
        dosis = "1000",
        unidadesCaja = 20,
        stock = 24,
        principioActivo = "Ebastina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-21"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Elontril 300",
        dosis = "1000",
        unidadesCaja = 30,
        stock = 41,
        principioActivo = "Bupropion",
        fechaInicioTratamiento = LocalDate.parse("2025-07-16"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    ),
    Medicina(
        name = "Heliocare",
        dosis = "1000",
        unidadesCaja = 60,
        stock = 20,
        principioActivo = "--",
        fechaInicioTratamiento = LocalDate.parse("2025-07-21"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-08-04")
    )
)