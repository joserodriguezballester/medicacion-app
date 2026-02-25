package com.example.prueba3.viewmodel

import android.R.attr.name
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.prueba3.data.local.entity.Medicina
import com.example.prueba3.data.preferences.PreferenciasApp
import com.example.prueba3.data.repository.MedicinaRepository
import com.example.prueba3.domain.MedicinaValidator
import com.example.prueba3.model.EstadoMedicinas
import com.example.prueba3.model.GrupoCompra
import com.example.prueba3.ui.model.FormularioMedicina
import com.example.prueba3.ui.model.toMedicina
import com.example.prueba3.ui.model.validar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
//import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.prueba3.ui.general.PantallaMedicinas
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * ViewModel que expone el estado y operaciones relacionadas con [Medicina].
 */
class MedicinaViewModel(
    private val repository: MedicinaRepository,
    private val context: Context
) : ViewModel() {

    // region Estado general y configuración

    var mostrarBotonVolver by mutableStateOf(false)
        private set

    private val preferencias = PreferenciasApp(context)

    private val _medicinas = MutableStateFlow<List<Medicina>>(emptyList())
    val medicinas: StateFlow<List<Medicina>> = _medicinas.asStateFlow()

    private val _modoCompacto = MutableStateFlow(true)
    val modoCompacto: StateFlow<Boolean> = _modoCompacto
    private val _modoCompactoGeneralScreen = MutableStateFlow(true)
    val modoCompactoGeneralScreen: StateFlow<Boolean> = _modoCompactoGeneralScreen
    val estadoMedicinas: StateFlow<EstadoMedicinas> = medicinas.map { lista ->
        val hoy = LocalDate.now()
        val activas = lista.filter { it.dosis != "0000" }.sortedBy { it.proximaFechaPedido }
        val inactivas = lista.filter { it.dosis == "0000" }.sortedBy { it.proximaFechaPedido }
        val enFarmacia = activas.filter { it.fechaEnFarmacia?.isBefore(hoy) == true }
        EstadoMedicinas(
            activas = activas,
            inactivas = inactivas,
            enFarmacia = enFarmacia
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        EstadoMedicinas(emptyList(), emptyList(), emptyList())
    )

    init {
        prepararDatosIniciales()
        observarMedicinas()
    }

    private fun prepararDatosIniciales() {
        viewModelScope.launch {
            var yaRealizada = preferencias.isCargaInicialYaRealizada()
         //   yaRealizada = false // ⚠️ Forzar carga: eliminar si no se desea
            if (!yaRealizada) {
                repository.cargaInicialSiNecesaria()
                preferencias.marcarCargaInicialRealizada()
            }
        }
    }

    private fun observarMedicinas() {
        viewModelScope.launch {
            repository.medicinas
                .catch { _mensaje.value = "Error al cargar medicinas: ${it.message}" }
                .collect { _medicinas.value = it }
        }
    }

    fun toggleModoCompacto() {
        _modoCompacto.value = !_modoCompacto.value
    }

    fun toggleModoCompactoGeneralScreen() {
        _modoCompactoGeneralScreen.value = !_modoCompactoGeneralScreen.value
    }

    fun setModoCompacto(valor: Boolean) {
        _modoCompacto.value = valor
    }

    // endregion


    // region UI: Mensajes, errores y búsqueda

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun setMensaje(texto: String) {
        _mensaje.value = texto
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    private val _resultadosBusqueda = MutableStateFlow<List<Medicina>>(emptyList())
    val resultadosBusqueda: StateFlow<List<Medicina>> = _resultadosBusqueda.asStateFlow()

    fun buscarPorNombre(nombre: String) {
        viewModelScope.launch {
            repository.searchPorNombre(nombre)
                .catch { _mensaje.value = "Error al buscar: ${it.message}" }
                .collect { _resultadosBusqueda.value = it }
        }
    }

    // endregion


    // region UI: Formulario y validación

    val formulario = FormularioMedicina()
    val errores = mutableStateMapOf<String, String?>()

    fun validarCampos(): Boolean {
        errores.clear()
        errores.putAll(formulario.validar())
        return errores.isEmpty()
    }

    // endregion


    // region Acciones sobre medicina seleccionada

    private val _medicina = MutableStateFlow<Medicina?>(null)
    val medicina: StateFlow<Medicina?> = _medicina

    fun getMedicinaById(medicinaId: Int, onResult: (Medicina?) -> Unit) {
        viewModelScope.launch {
            val medicina = repository.getById(medicinaId)
            onResult(medicina)
        }
    }

    fun loadMedicinaById(medicinaId: Int) {
        viewModelScope.launch {
            _medicina.value = repository.getById(medicinaId)
        }
    }

    // endregion


    // region CRUD completo

    fun insertMedicina(medicina: Medicina) {
        viewModelScope.launch {
            val id = repository.insert(medicina)
            _mensaje.value =
                if (id == -1L) "La medicina ya existe." else "Medicina añadida correctamente."
        }
    }

    fun updateMedicina(medicina: Medicina) {
        viewModelScope.launch {
            val filas = repository.update(medicina)
            if (filas == 0) _mensaje.value =
                "No se pudo actualizar la medicina." else _mensaje.value =
                "Medicina actualizada correctamente."
        }
    }

    fun deleteMedicina(medicina: Medicina) {
        viewModelScope.launch {
            val filas = repository.delete(medicina)
            if (filas == 0) _mensaje.value =
                "No se pudo eliminar la medicina." else _mensaje.value = "Medicina eliminada"
        }
    }

    fun eliminarTodo() {
        viewModelScope.launch { repository.deleteAll() }
    }

    // endregion


    // region Acciones rápidas

    fun actualizarDosis(id: Int, nuevaDosis: String) {
        _medicinas.value.find { it.id == id }?.let {
            val medicinaActualizada = it.copy(dosis = nuevaDosis)
            viewModelScope.launch {
                val filas = repository.update(medicinaActualizada)
                _mensaje.value =
                    if (filas > 0) "Dosis actualizada correctamente" else "No se pudo actualizar la dosis"
                println("Mensaje actualizado: ${_mensaje.value}")
            }
        }
    }

    fun actualizarStock(id: Int, nuevoStock: Int) {
        _medicinas.value.find { it.id == id }?.let {
            val medicinaActualizada = it.copy(stock = nuevoStock, fechaStock = LocalDate.now())
            viewModelScope.launch {
                val filas = repository.update(medicinaActualizada)
                _mensaje.value =
                    if (filas > 0) "Stock actualizado correctamente" else "No se pudo actualizar el stock"
            }
        }
    }

    // endregion


    // region Validar y procesar formulario

    fun validarYGuardar(navController: NavHostController) {
        if (!validarCampos()) {
            setMensaje("Por favor corrige los campos marcados.")
            return
        }
        insertMedicina(formulario.toMedicina())
        navController.popBackStack()
    }

    fun validarYActualizar(navController: NavHostController) {
        if (!validarCampos()) {
            setMensaje("Corrige los errores antes de guardar.")
            return
        }
        updateMedicina(formulario.toMedicina(base = medicina.value))
        navController.popBackStack()
    }

    fun validarDosis(nuevaDosis: String): Boolean {
        val error = MedicinaValidator.validarDosis(nuevaDosis)
        return error.isNullOrEmpty()
    }

    fun validarYActualizarDosis(id: Int, nuevaDosis: String): Boolean {
        if (!validarDosis(nuevaDosis)) {
            setMensaje("Corrige los errores antes de guardar.")
            return false
        }
        actualizarDosis(id, nuevaDosis)
        return true
    }


// endregion


    val medicinasActivasOrdenadas: StateFlow<List<Medicina>> = medicinas
        .map { lista ->
            lista.filter { it.dosis != "0000" }
                .sortedBy { it.semanasPendientes }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    private val _pantallaActual = MutableStateFlow<PantallaMedicinas>(PantallaMedicinas.General)
    val pantallaActual: StateFlow<PantallaMedicinas> = _pantallaActual


    private val _listaCompra = MutableStateFlow<GrupoCompra?>(null)
    val listaCompra: StateFlow<GrupoCompra?> = _listaCompra

    private val _mostrarListaCompra = MutableStateFlow(false)
    val mostrarListaCompra: StateFlow<Boolean> = _mostrarListaCompra
    private val _listaTratamiento = MutableStateFlow<List<Medicina>>(emptyList())
    val listaTratamiento: StateFlow<List<Medicina>> = _listaTratamiento

    private val _mostrarListaTratamiento = MutableStateFlow(false)
    val mostrarListaTratamiento: StateFlow<Boolean> = _mostrarListaTratamiento


    private val _listaBD = MutableStateFlow<List<Medicina>>(emptyList())
    val listaBD: StateFlow<List<Medicina>> = _listaBD
    private val _mostrarListaBD = MutableStateFlow(false)
    val mostrarListaBD: StateFlow<Boolean> = _mostrarListaBD

    private val _listaRenovar = MutableStateFlow<List<Medicina>>(emptyList())
    val listaRenovar: StateFlow<List<Medicina>> = _listaRenovar
    private val _mostrarListaRenovar = MutableStateFlow(false)
    val mostrarListaRenovar: StateFlow<Boolean> = _mostrarListaRenovar


    fun cerrarListaCompra() {
        _mostrarListaCompra.value = false
    }

    fun cerrarListaTratamiento() {
        _mostrarListaTratamiento.value = false
    }

    fun generarTextoListaCompra(): String {
        val grupo = listaCompra.value ?: return "No hay medicinas en la lista de compra."

        val disponibles = grupo.enFarmacia.joinToString("\n") {
            "✅ ${it.name} (${it.semanasPendientes} semanas)"
        }
        val noDisponibles = grupo.noEnFarmacia.joinToString("\n") {
            "❌ ${it.name} (${it.semanasPendientes} semanas)"
        }

        return "🛒 Lista de Compra:\n\n$disponibles\n\n$noDisponibles"
    }

    fun cerrarListaBD() {
        _mostrarListaBD.value = false
    }

    fun cerrarListaRenovar() {
        _mostrarListaRenovar.value = false
    }

    fun enviarListaRenovar() {
        resetearFlagsPantalla()
        val hoy = LocalDate.now()
        val estadoActual = estadoMedicinas.value
        val medicinasActivas = estadoActual.activas

        println("Total medicinas activas: ${medicinasActivas.size}")

        val renovar = medicinasActivas.filter { medicina ->
            val diasRestantes = ChronoUnit.DAYS.between(hoy, medicina.fechaFinTratamiento)
            val diasCaja = medicina.unidadesCaja / medicina.consumoDiario

            val incluir = diasRestantes <= 30 || diasCaja > diasRestantes
            println(
                "Medicina: ${medicina.name} | " +
                        "Fin tratamiento: ${medicina.fechaFinTratamiento} | " +
                        "Días restantes: $diasRestantes | " +
                        "Días que cubre una caja: $diasCaja | " +
                        "¿Renovar?: $incluir"
            )
            incluir
        }
        _listaRenovar.value = renovar
        println("Total medicinas en _listaRenovar: ${_listaRenovar.value.size}")
        _mostrarListaRenovar.value = true
    }

    fun enviarListaTratamiento() {
        resetearFlagsPantalla()
        val estadoActual = estadoMedicinas.value
        val medicinasActivas = estadoActual.activas.sortedByDescending { it.dosis }

        if (medicinasActivas.isNotEmpty()) {
            _listaTratamiento.value = medicinasActivas
            _mostrarListaTratamiento.value = true
        }
    }

    fun enviarListaBD() {
        resetearFlagsPantalla()
        val estadoActual = estadoMedicinas.value
        val todasLasMedicinas = (estadoActual.activas + estadoActual.inactivas)
            .sortedBy { it.dosis }

        if (todasLasMedicinas.isNotEmpty()) {
            _listaBD.value = todasLasMedicinas
            _mostrarListaBD.value = true
        }
    }

    fun enviarListaCompra() {
        resetearFlagsPantalla()
        val estado = estadoMedicinas.value
        val bajoStock = estado.activas.filter { it.semanasPendientes < 3 }
        val enFarmacia = estado.enFarmacia

        val en = bajoStock
            .filter { m -> enFarmacia.any { it.id == m.id } }
            .sortedBy { it.semanasPendientes }
        val no = bajoStock
            .filter { m -> enFarmacia.none { it.id == m.id } }
            .sortedBy { it.semanasPendientes }

        _listaCompra.value = GrupoCompra(enFarmacia = en, noEnFarmacia = no)
        _mostrarListaCompra.value = true
    }

    fun generarTextoListaBD(): String {
        val lista = listaBD.value
        if (lista.isEmpty()) return "No hay medicinas en la lista de BD."

        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val texto = lista.joinToString(separator = ",\n\n") { medicina ->
            """
        Medicina(
            name = "${medicina.name}",
            principioActivo = "${medicina.principioActivo}",
            dosis = "${medicina.dosis}",
            unidadesCaja = ${medicina.unidadesCaja},
            stock = ${medicina.stock},
            fechaInicioTratamiento = LocalDate.parse("${
                medicina.fechaInicioTratamiento!!.format(
                    formato
                )
            }", formato),
            fechaFinTratamiento = LocalDate.parse("${medicina.fechaFinTratamiento!!.format(formato)}", formato),
            fechaStock = LocalDate.parse("${medicina.fechaStock!!.format(formato)}", formato)
        )
        """.trimIndent()
        }

        return "listOf(\n\n$texto\n\n)"

    }

    fun actualizarPantalla() {
        _pantallaActual.value = when {
            mostrarListaBD.value && listaBD.value.isNotEmpty() -> PantallaMedicinas.BD(listaBD.value)
            mostrarListaRenovar.value && listaRenovar.value.isNotEmpty() -> PantallaMedicinas.Renovar(
                listaRenovar.value
            )

            mostrarListaTratamiento.value && listaTratamiento.value.isNotEmpty() -> PantallaMedicinas.Tratamiento(
                listaTratamiento.value
            )

            mostrarListaCompra.value && (listaCompra.value?.enFarmacia?.isNotEmpty() == true ||
                    listaCompra.value?.noEnFarmacia?.isNotEmpty() == true
                    ) -> PantallaMedicinas.Compra(listaCompra.value!!)

            else -> PantallaMedicinas.General
        }
    }

    fun volverAPantallaGeneral() {
        resetearFlagsPantalla()
        actualizarPantalla()
    }

    fun enviarCompraYActualizar() {
        enviarListaCompra()
        actualizarPantalla()
    }

    fun enviarTratamientoYActualizar() {
        enviarListaTratamiento()
        actualizarPantalla()
    }

    fun enviarRenovarYActualizar() {
        enviarListaRenovar()
        actualizarPantalla()
    }

    fun enviarBDYActualizar() {
        enviarListaBD()
        actualizarPantalla()
    }

    fun resetearFlagsPantalla() {
        _mostrarListaBD.value = false
        _mostrarListaRenovar.value = false
        _mostrarListaTratamiento.value = false
        _mostrarListaCompra.value = false
    }

    fun generarTextoRenovar(lista: List<Medicina>): String {
        var texto: String
        val nombres = lista.map { it.name }

        val encadenados = when (nombres.size) {
            0 -> ""
            1 -> nombres[0]
            else -> nombres.dropLast(1).joinToString(", ") + " y " + nombres.last()
        }

        if (encadenados.isEmpty()) {
            texto = "No hay medicamentos para renovar."
        } else {
            texto = "Acordarse que hay que renovar: $encadenados"
        }
        println(texto)
        return texto
    }

    fun generarTextoBD(lista: List<Medicina>): String {
        val sb = StringBuilder()
        sb.append("val listaMedicinasA = listOf( ")
        lista.forEachIndexed { index, med ->
            sb.append("    Medicina( ")
            sb.append("name = \"${med.name}\", ")
            sb.append("dosis = \"${med.dosis}\", ")
            sb.append("unidadesCaja = ${med.unidadesCaja}, ")
            sb.append("stock = ${med.stock}, ")
            sb.append("principioActivo = \"${med.principioActivo}\", ")
            sb.append("fechaInicioTratamiento = LocalDate.parse(\"${med.fechaInicioTratamiento}\"), ")
            sb.append("fechaFinTratamiento = LocalDate.parse(\"${med.fechaFinTratamiento}\"), ")
            sb.append("fechaStock = LocalDate.parse(\"${med.fechaStock}\") ")
            sb.append("    )")

            if (index != lista.lastIndex) sb.append(", ") else sb.append(" ")
        }

        sb.append(") ")
        return sb.toString()
    }


    fun generarTextoTratamiento(lista: List<Medicina>): String {
        val encabezado = String.format("%-15s %3s %3s %3s %3s", "Nombre", "☕", "🍽️", "🌙", "🛏️")

        val filas = lista.map { medicina ->
            val dosis = medicina.dosis
            String.format(
                "%-15s %3s %3s %3s %3s",
                medicina.name,
                if (dosis[0] != '0') dosis[0].toString() else "",
                if (dosis[1] != '0') dosis[1].toString() else "",
                if (dosis[2] != '0') dosis[2].toString() else "",
                if (dosis[3] != '0') dosis[3].toString() else ""
            )
        }
        val texto = (listOf(encabezado) + filas).joinToString("\n")
        println(texto)
        return texto
    }

    var mensajeCompartir by mutableStateOf("")
    fun actualizarMensajeCompartir(mensaje: String) {
        mensajeCompartir = mensaje
    }

    fun activarVolver() {
        mostrarBotonVolver = true
    }

    fun desactivarVolver() {
        mostrarBotonVolver = false
    }

}
