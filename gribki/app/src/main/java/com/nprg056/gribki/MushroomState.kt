package com.nprg056.gribki

import Mushroom
import UsageType

data class MushroomState(
    val mushrooms: List<Mushroom> = emptyList(),
    val name: String ="",
    val desc: String ="",
    val loc: String ="",
    val usage: UsageType = UsageType.jedla,
    val image: Int = 0,
    val sortType: SortType = sortType.NAME

)
