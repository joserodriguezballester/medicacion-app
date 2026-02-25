package com.example.prueba3.data.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// 👇 Extension para crear el DataStore asociada a Context
val Context.dataStore by preferencesDataStore(name = "ajustes")

class PreferenciasApp(private val context: Context) {

    private val CARGA_INICIAL_KEY = booleanPreferencesKey("carga_inicial_realizada")

    suspend fun isCargaInicialYaRealizada(): Boolean {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) // 👈 FIX aquí
                else throw exception
            }
            .map { prefs: Preferences ->
                prefs[CARGA_INICIAL_KEY] ?: false
            }
            .first()
    }

    suspend fun marcarCargaInicialRealizada() {
        context.dataStore.edit { prefs ->
            prefs[CARGA_INICIAL_KEY] = true
        }
    }
}
