package com.nprg056.gribki.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nprg056.gribki.MushroomRepository
import kotlinx.coroutines.flow.*

class MushroomDetailViewModel(
    private val repository: MushroomRepository
) : ViewModel() {

    private val _selectedId = MutableStateFlow<Int?>(null)

    val state: StateFlow<MushroomDetailState> = _selectedId
        .filterNotNull()
        .flatMapLatest { id ->
            repository.getMushroomById(id)
        }
        .map { MushroomDetailState(selectedMushroom = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MushroomDetailState())

    fun onEvent(event: MushroomDetailEvent) {
        when (event) {
            is MushroomDetailEvent.LoadMushroom -> _selectedId.value = event.id
        }
    }
}

