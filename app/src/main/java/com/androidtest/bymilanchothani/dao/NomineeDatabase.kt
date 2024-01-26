package com.cube.cubeacademy.lib.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidtest.bymilanchothani.R
import com.androidtest.bymilanchothani.models.Nominee

@Database(entities = [Nominee::class], version = 1, exportSchema = false)
abstract class NomineeDatabase : RoomDatabase() {

    abstract fun nomineeDao(): NomineeDao

    companion object {
        @Volatile
        private var INSTANCE: NomineeDatabase? = null

        fun getDatabase(context: Context): NomineeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NomineeDatabase::class.java,
                    context.getString(R.string.nominee_database)
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}