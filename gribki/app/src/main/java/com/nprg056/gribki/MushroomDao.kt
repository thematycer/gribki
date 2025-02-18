package com.nprg056.gribki


import Mushroom
import com.nprg056.gribki.UsageType
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

    @Query("SELECT * FROM mushroom ORDER BY name ASC")
    fun getMushroomByName(): Flow<List<Mushroom>>

    @Query("SELECT * FROM mushroom WHERE usage = :usage ORDER BY name ASC")
    fun getMushroomsByUsage(usage: UsageType): Flow<List<Mushroom>>


}