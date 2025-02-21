package com.nprg056.gribki



import com.nprg056.gribki.Mushroom
import com.nprg056.gribki.UsageType
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


//here you write sql queries
@Dao
interface MushroomDao {

    @Upsert
    fun insert(mushroom: Mushroom)

    @Delete
    fun delete(mushroom: Mushroom)

    @Query("SELECT * FROM mushroom ORDER BY name ASC")
    fun getMushroomByName(): Flow<List<Mushroom>>

    @Query("SELECT * FROM mushroom WHERE usage = :usageType ORDER BY name ASC")
    fun getMushroomsByUsage(usageType: UsageType): Flow<List<Mushroom>>


    //can add to select by name if needed
    @Query("SELECT * FROM mushroom WHERE id = :id")
    fun getMushroomById(id: Int):  Flow<List<Mushroom>>
}
