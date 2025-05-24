package com.nprg056.gribki.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nprg056.gribki.MushroomRepository
import com.nprg056.gribki.settings.SettingsViewModel

class SettingsViewModelFactory(
    private val repository: MushroomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repository) as T
    }
}
