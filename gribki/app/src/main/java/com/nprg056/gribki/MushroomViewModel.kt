package com.nprg056.gribki

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nprg056.gribki.UsageType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn


@OptIn(ExperimentalCoroutinesApi::class)
class MushroomViewModel(
    private val dao: MushroomDao
):ViewModel() {
    private val _state = MutableStateFlow(MushroomState())
    private val _usageType = MutableStateFlow(UsageType.Vsechny)
    private val _currentId = MutableStateFlow(0)
    private val _searchedName = MutableStateFlow("")
    private val _fontSize = MutableStateFlow(16f)
    // actual list of mushrooms according to type/name searched
    private val _mushrooms = _usageType
        .combine(_searchedName) { usageType, searchedName ->
            when (usageType) {
                UsageType.Vsechny -> {dao.searchMushroomsByName(searchedName)}
                else -> {dao.getMushroomByNameAndUsage(searchedName, usageType)}
            }
        }
        .flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    // if any of the variables changes -> change state in mushroomState
    val state: StateFlow<MushroomState> = combine(
        _mushrooms,
        _usageType,
        _currentId,
        _searchedName,
        _fontSize
    ) { mushrooms, usageType, currentId, searchedName, fontSize ->
        MushroomState(
            mushrooms = mushrooms,
            usage = usageType,
            searchedName = searchedName,
            fontSize = fontSize
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MushroomState())


    // on user event->call something from mushroomEvent
    fun  onEvent(event: MushroomEvent){
        when(event){
            is MushroomEvent.SortMushroom->{
                _usageType.value = event.usageType
            }
            is MushroomEvent.GetOneMushroom->{
                _currentId.value = event.id
                _usageType.value = UsageType.Vsechny
            }
            is MushroomEvent.SearchMushroomName->{
                _searchedName.value = event.name
            }
            is MushroomEvent.ChangeFontSize -> {
                _fontSize.value = event.fontSize
            }
        }
    }
}