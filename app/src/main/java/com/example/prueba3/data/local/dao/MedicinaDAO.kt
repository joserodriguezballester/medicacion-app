package com.example.prueba3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.prueba3.data.local.entity.Medicina
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object para operaciones CRUD sobre la entidad [Medicina].
 */
@Dao
interface MedicinaDAO {

    /**
     * Inserta una nueva medicina. Si ya existe (por ID), la ignora.
     * @return ID de la medicina insertada, o -1 si fue ignorada.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedicina(medicina: Medicina): Long

    /**
     * Actualiza una medicina existente en la base de datos.
     * @return número de filas modificadas.
     */
    @Update
    suspend fun updateMedicina(medicina: Medicina): Int

    /**
     * Elimina una medicina específica.
     * @return número de filas eliminadas.
     */
    @Delete
    suspend fun deleteMedicina(medicina: Medicina): Int

    /**
     * Elimina todas las medicinas de la tabla.
     * @return número de filas eliminadas.
     */
    @Query("DELETE FROM medicina_data_table")
    suspend fun deleteAll(): Int

 /**
     * Obtiene todas las medicinas como un flujo reactivo.
     */
    @Query("SELECT * FROM ${Medicina.TABLE_NAME}")
    fun getAllMedicinas(): Flow<List<Medicina>>

    /**
     * Obtiene una lista estática de todas las medicinas (no reactivo).
     */
    @Query("SELECT * FROM ${Medicina.TABLE_NAME}")
    fun getListMedicinas(): List<Medicina>

    /**
     * Obtiene una medicina por su ID.
     * @param id identificador único.
     */
    @Query("SELECT * FROM ${Medicina.TABLE_NAME} WHERE id = :id")
    suspend fun getMedicinaById(id: Int): Medicina?

    /**
     * Busca medicinas por coincidencia parcial en el nombre.
     */
    @Query("SELECT * FROM ${Medicina.TABLE_NAME} WHERE name LIKE '%' || :name || '%'")
    fun searchMedicinaPorNombre(name: String): Flow<List<Medicina>>

    /**
     * Elimina una medicina directamente por ID.
     * @return número de filas eliminadas.
     */
    @Query("DELETE FROM ${Medicina.TABLE_NAME} WHERE id = :id")
    suspend fun deleteMedicinaById(id: Int): Int

    /**
     * Devuelve la cantidad total de medicinas en la base de datos.
     */
    @Query("SELECT COUNT(*) FROM ${Medicina.TABLE_NAME}")
    suspend fun getCantidadMedicinas(): Int
}