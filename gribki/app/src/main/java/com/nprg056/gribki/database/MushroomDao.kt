package com.nprg056.gribki.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface MushroomDao {

    @Upsert
    suspend fun insert(mushroom: Mushroom)

    //@Delete
    //suspend fun delete(mushroom: Mushroom)

    @Query("SELECT * FROM mushroom WHERE name LIKE '%'|| :name || '%' ORDER BY name ASC")
    fun searchMushroomsByName(name: String): Flow<List<Mushroom>>

    @Query("SELECT * FROM mushroom WHERE name LIKE '%'||:name || '%' AND usage = :usageType ORDER BY name ASC")
    fun getMushroomByNameAndUsage(name: String, usageType: UsageType): Flow<List<Mushroom>>

    @Query("SELECT * FROM mushroom WHERE id = :id")
    fun getMushroomById(id: Int): Flow<Mushroom>

    //can add to select by name if needed
    //@Query("SELECT * FROM mushroom WHERE id = :id")
    //fun getMushroomById(id: Int):  Flow<List<Mushroom>>
}
