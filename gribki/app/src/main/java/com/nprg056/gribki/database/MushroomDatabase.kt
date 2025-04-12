package com.nprg056.gribki.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Mushroom::class],
    version = 1
)

abstract class MushroomDatabase : RoomDatabase() {
    abstract fun MushroomDao(): MushroomDao
}