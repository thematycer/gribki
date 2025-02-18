package com.nprg056.gribki

import androidx.lifecycle.ViewModel
import com.nprg056.gribki.UsageType
import com.nprg056.gribki.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MushroomViewModel(
    private val dao: MushroomDao
):ViewModel() {
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _usageType = MutableStateFlow(UsageType.jedla)
    private val _state = MutableStateFlow(MushroomState())
    private val _mushrooms = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.NAME -> {
                    dao.getMushroomByName()
                }
                SortType.USAGE_TYPE -> {
                    dao.getMushroomsByUsage(_usageType)
                }
            }
        }

    fun  onEvent(event: MushroomEvent){
        when(event){
            is MushroomEvent.SortMushroom->{
                _sortType.value = event.sortType
            }
        }
    }
}