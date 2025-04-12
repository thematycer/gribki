package com.nprg056.gribki.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nprg056.gribki.MushroomRepository
import kotlinx.coroutines.flow.*

class SettingsViewModel(
    private val repository: MushroomRepository
) : ViewModel() {

    private val _fontSize = MutableStateFlow(16f)

    val state: StateFlow<SettingsState> = _fontSize
        .combine(_fontSize) { fontSize, _ ->
            SettingsState(fontSize = fontSize)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SettingsState())



    init {
        _fontSize.value = repository.loadFontSize()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeFontSize -> _fontSize.value = event.fontSize
            is SettingsEvent.SaveSettings -> saveFontSettings()
        }
    }

    private fun saveFontSettings() {
        repository.saveFontSize(_fontSize.value)
    }
}
