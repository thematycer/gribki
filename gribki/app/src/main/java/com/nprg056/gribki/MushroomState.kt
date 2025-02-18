package com.nprg056.gribki

import Mushroom
import com.nprg056.gribki.UsageType

data class MushroomState(
    val mushrooms: List<Mushroom> = emptyList(),
    val name: String ="",
    val desc: String ="",
    val loc: String ="",
    val usage: UsageType = UsageType.jedla,
    val image: Int = 0,
    val sortType: SortType = SortType.NAME

)
