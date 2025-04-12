package com.nprg056.gribki.detail

sealed interface MushroomDetailEvent {
    data class LoadMushroom(val id: Int) : MushroomDetailEvent
}
