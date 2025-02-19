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
    //pri zmene event.sortype se zavola
    private val _mushrooms = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.NAME -> {
                    dao.getMushroomByName()
                }
                SortType.USAGE_TYPE -> {
                    dao.getMushroomsByUsage(_usageType.value)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(MushroomState())
    private val _usageType = MutableStateFlow(UsageType.jedla)
    private val _currentId = MutableStateFlow(0);
    private val _currentMushroom = _currentId
        .flatMapLatest { id ->
            dao.getMushroomById(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    //pri zmene se abdejtuj
    val state = combine(_state, _sortType, _mushrooms, _usageType, _currentId){state, sortType, mushrooms, usageType, currentId->
        state.copy(
            mushrooms = mushrooms,
            sortType = sortType,
            usage = usageType,
            mushroomId = currentId
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MushroomState())






    fun  onEvent(event: MushroomEvent){
        when(event){
            is MushroomEvent.SortMushroom->{
                _sortType.value = event.sortType
            }
            is MushroomEvent.GetOneMushroom->{
                _currentId.value = event.id
            }

        }
    }
}