package com.nprg056.gribki

import Mushroom

data class MushroomState(
    val mushrooms: List<Mushroom> = emptyList(),
    val name: String ="",
    val usage: UsageType = UsageType.jedla,
    val mushroomId: Int = 0,
    val sortType: SortType = SortType.NAME

)
