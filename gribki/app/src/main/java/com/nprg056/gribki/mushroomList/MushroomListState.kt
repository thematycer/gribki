package com.nprg056.gribki.mushroomList

import com.nprg056.gribki.database.Mushroom
import com.nprg056.gribki.database.UsageType

data class MushroomListState(
    val mushrooms: List<Mushroom> = emptyList(),
    val searchedName: String = "",
    val usage: UsageType = UsageType.Vsechny
)
