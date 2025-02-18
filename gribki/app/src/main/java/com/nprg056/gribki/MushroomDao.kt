package com.nprg056.gribki


import Mushroom
import UsageType
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface MushroomDao {

    @Upsert
    suspend fun insert(mushroom: Mushroom)

    @Delete
    suspend fun delete(mushroom: Mushroom)

    @Query("SELECT * from mushroom ORDER BY name ASC")
    fun getMushroomByName(): Flow<List<Mushroom>>?

    @Query("SELECT * from Mushroom WHERE id = :id")
    fun getMushroomByID(id: Int): Flow<Mushroom?>

    @Query("SELECT * FROM Mushroom WHERE usage = :usageType ORDER BY name ASC")
    fun getMushroomsByUsage(usageType: UsageType): Flow<List<Mushroom>>

    @Query("SELECT * FROM Mushroom")
    fun getAllMushrooms(): Flow<List<Mushroom>>

}