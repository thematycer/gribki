package com.nprg056.gribki

//user actions with the database
sealed interface MushroomEvent {
    //get list of mushrooms
    data class SortMushroom(val usageType: UsageType):MushroomEvent

    //get single mushroom in a list
    data class GetOneMushroom(val id: Int):MushroomEvent

    data class SearchMushroomName(val name: String):MushroomEvent

    data class ChangeFontSize(val fontSize: Float) : MushroomEvent
}