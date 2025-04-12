package com.nprg056.gribki.database
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
data class Mushroom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //name of mushroom
    val name: String,
    //description of the mushroom
    val desc: String,
    //location of the mushroom
    val loc: String,
    //type of mushroom usage
    val usage: UsageType,
    //id of the mushroom image
    val imageID: Int,
    //list tagu pro houby
    //val tags: List<String>
)
