package com.example.prueba3.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.prueba3.data.Converts.Converters
import com.example.prueba3.data.local.dao.MedicinaDAO
import com.example.prueba3.data.local.entity.Medicina


@Database(entities = [Medicina::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MedicinaDatabase : RoomDatabase() {
    abstract val medicinaDAO: MedicinaDAO

    companion object {
        private const val DATABASE_NAME = "medicina_data_database"
        @Volatile
        private var INSTANCE: MedicinaDatabase? = null


        fun getInstance(context: Context): MedicinaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MedicinaDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true) // opcional en desarrollo
                    .build()
                    .also { INSTANCE = it }
            }
        }

    }
}
