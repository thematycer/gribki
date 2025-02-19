package com.nprg056.gribki

//user actions with the database
sealed interface MushroomEvent {
    //get list of mushrooms
    data class SortMushroom(val sortType: SortType):MushroomEvent

    //get single mushroom
    data class GetOneMushroom(val id: Int):MushroomEvent

}