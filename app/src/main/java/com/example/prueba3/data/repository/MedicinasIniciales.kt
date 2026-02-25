package com.example.prueba3.data.repository

import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.utils.pasarAFechaddMMyyyy
import java.time.LocalDate
import java.time.format.DateTimeFormatter


val formato = DateTimeFormatter.ISO_LOCAL_DATE
val fechafin = LocalDate.parse("2026-06-30", formato)
val fechastock = LocalDate.parse("2025-07-18", formato)

val listaInicial = listOf(
    Medicina(
        name = "Venlafaxina 300",
        dosis = "1000",
        unidadesCaja = 30,
        stock = 40,
        principioActivo = "Venlafaxina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-18", formato),
        fechaFinTratamiento = fechafin,
        fechaStock = LocalDate.parse("2025-07-18", formato)
    ),

    Medicina(
        name = "Elontril 300",
        principioActivo = "Bupropion",
        dosis = "1000",
        unidadesCaja = 30,
        stock = 28,
        fechaInicioTratamiento = LocalDate.parse("2025-07-16", formato),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),

    Medicina(
        name = "Ebastel",
        principioActivo = "Ebastina",
        dosis = "1000",
        unidadesCaja = 20,
        stock = 20,
        fechaInicioTratamiento = "21/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),

    Medicina(
        name = "Heliocare",
        principioActivo = "--",
        dosis = "1000",
        unidadesCaja = 60,
        stock = 5,
        fechaInicioTratamiento = "21/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),

    Medicina(
        name = "Largactil 25",
        dosis = "0220",
        unidadesCaja = 50,
        stock = 5,
        principioActivo = "Clorpromazina",
        fechaInicioTratamiento = "25/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),

    Medicina(
        name = "Rexer",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 30,
        principioActivo = "Mirtazapina",
        fechaInicioTratamiento = "08/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),
    Medicina(
        name = "Dormodor ",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 30,
        principioActivo = "Flurazepam",
        fechaInicioTratamiento = "10/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),
    Medicina(
        name = "Diazepam",
        dosis = "0002",
        unidadesCaja = 40,
        stock = 34,
        principioActivo = "--",
        fechaInicioTratamiento = "20/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),
    Medicina(
        name = "Quetiapina",
        dosis = "0001",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = "07/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),
    Medicina(
        name = "Quetiapina1",
        dosis = "0000",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = "07/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    ),
    Medicina(
        name = "Quetiapina2",
        dosis = "0000",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = "07/07/2025".pasarAFechaddMMyyyy(),
        fechaFinTratamiento = fechafin,
        fechaStock = fechastock
    )
)


val listaMedicinasA = listOf(
    Medicina(
        name = "Quetiapina1",
        dosis = "0000",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-07"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Quetiapina2",
        dosis = "0000",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-07"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Rexer",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 30,
        principioActivo = "Mirtazapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-08"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Dormodor ",
        dosis = "0001",
        unidadesCaja = 30,
        stock = 30,
        principioActivo = "Flurazepam",
        fechaInicioTratamiento = LocalDate.parse("2025-07-10"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Quetiapina",
        dosis = "0001",
        unidadesCaja = 60,
        stock = 100,
        principioActivo = "Quetiapina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-07"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Diazepam",
        dosis = "0002",
        unidadesCaja = 40,
        stock = 34,
        principioActivo = "--",
        fechaInicioTratamiento = LocalDate.parse("2025-07-20"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Largactil 25",
        dosis = "0220",
        unidadesCaja = 50,
        stock = 5,
        principioActivo = "Clorpromazina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-25"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Ebastel",
        dosis = "1000",
        unidadesCaja = 20,
        stock = 20,
        principioActivo = "Ebastina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-21"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Elontril 300",
        dosis = "1000",
        unidadesCaja = 30,
        stock = 28,
        principioActivo = "Bupropion",
        fechaInicioTratamiento = LocalDate.parse("2025-07-16"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Venlafaxina 300",
        dosis = "1000",
        unidadesCaja = 30,
        stock = 40,
        principioActivo = "Venlafaxina",
        fechaInicioTratamiento = LocalDate.parse("2025-07-18"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    ),
    Medicina(
        name = "Heliocare",
        dosis = "1000",
        unidadesCaja = 60,
        stock = 5,
        principioActivo = "--",
        fechaInicioTratamiento = LocalDate.parse("2025-07-21"),
        fechaFinTratamiento = LocalDate.parse("2026-06-30"),
        fechaStock = LocalDate.parse("2025-07-18")
    )
)