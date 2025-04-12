package com.nprg056.gribki.mushroomList

import com.nprg056.gribki.database.UsageType

sealed interface MushroomListEvent {
    data class SortMushroom(val usageType: UsageType) : MushroomListEvent
    data class Search(val name: String) : MushroomListEvent
}
