package com.nprg056.gribki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Mushroom::class],
    version = 1
)

abstract class MushroomDatabase : RoomDatabase() {
    abstract fun MushroomDao(): MushroomDao

    companion object {
        @Volatile
        private var INSTANCE: MushroomDatabase? = null

        fun getInstance(context: Context): MushroomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MushroomDatabase::class.java,
                    "mushroom_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}