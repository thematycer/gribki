package com.nprg056.gribki

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nprg056.gribki.UsageType
import com.nprg056.gribki.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class MushroomViewModel(
    private val dao: MushroomDao
):ViewModel() {
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _state = MutableStateFlow(MushroomState())
    private val _usageType = MutableStateFlow(UsageType.jedla)
    private val _currentId = MutableStateFlow(0);

    //actual list of mushrooms according to type/name searched
    private val _mushrooms = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.NAME -> {
                    dao.getMushroomByName()
                }
                SortType.USAGE_TYPE -> {
                    dao.getMushroomsByUsage(_usageType.value)
                }
                SortType.ONE_BY_ID->{
                    dao.getMushroomById(_currentId.value)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    //if any of the variables changes-> change state in mushroomState
    val state = combine(_state, _sortType, _mushrooms, _usageType, _currentId){state, sortType, mushrooms, usageType, currentId ->
        state.copy(
            mushrooms = mushrooms,
            sortType = sortType,
            usage = usageType,
            mushroomId = currentId,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MushroomState())





    //on user event->call something from mushroomEvent
    fun  onEvent(event: MushroomEvent){
        when(event){
            is MushroomEvent.SortMushroom->{
                _sortType.value = event.sortType
            }
            is MushroomEvent.GetOneMushroom->{
                _currentId.value = event.id
                _sortType.value = SortType.ONE_BY_ID
            }
        }
    }
}