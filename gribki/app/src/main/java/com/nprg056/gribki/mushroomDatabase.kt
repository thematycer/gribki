package com.nprg056.gribki


import Mushroom
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Mushroom::class],
    version = 1
)
abstract class mushroomDatabase : RoomDatabase() {
    abstract val dao: MushroomDao
}