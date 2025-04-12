package com.nprg056.gribki.mushroomList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nprg056.gribki.MushroomRepository
import com.nprg056.gribki.database.UsageType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


@OptIn(ExperimentalCoroutinesApi::class)
class MushroomListViewModel(
    private val repository: MushroomRepository
): ViewModel() {

    private val _usageType = MutableStateFlow(UsageType.Vsechny)
    private val _searchedName = MutableStateFlow("")

    private val _mushrooms = _usageType
        .combine(_searchedName) { usageType, searchedName ->
            when (usageType) {
                UsageType.Vsechny -> repository.searchMushroomsByName(searchedName)
                else -> repository.getMushroomByNameAndUsage(searchedName, usageType)
            }
        }
        .flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state: StateFlow<MushroomListState> = combine(
        _mushrooms, _usageType, _searchedName
    ) { mushrooms, usage, name ->
        MushroomListState(
            mushrooms = mushrooms,
            usage = usage,
            searchedName = name
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MushroomListState())

    fun onEvent(event: MushroomListEvent) {
        when (event) {
            is MushroomListEvent.SortMushroom -> _usageType.value = event.usageType

            is MushroomListEvent.Search -> _searchedName.value = event.name
        }
    }
}
