package com.nprg056.gribki



import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Mushroom::class],
    //version will be kept 1, since I am not sure how to do migration
    version = 1
)

abstract class MushroomDatabase : RoomDatabase() {
    abstract fun MushroomDao(): MushroomDao
}