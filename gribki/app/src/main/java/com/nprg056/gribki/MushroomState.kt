package com.nprg056.gribki

import com.nprg056.gribki.Mushroom
//information for the view model (what is current name set, what id, ...)
data class MushroomState(
    val mushrooms: List<Mushroom> = emptyList(),
    val searchedName: String ="",
    val usage: UsageType = UsageType.jedla,
    val mushroomId: Int = 0,
)
