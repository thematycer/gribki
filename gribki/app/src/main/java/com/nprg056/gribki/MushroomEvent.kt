package com.nprg056.gribki

//user actions
sealed interface MushroomEvent {
    data class SortMushroom(val sortType: SortType):MushroomEvent


}