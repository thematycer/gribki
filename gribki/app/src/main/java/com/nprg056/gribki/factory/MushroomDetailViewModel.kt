package com.nprg056.gribki.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nprg056.gribki.MushroomRepository
import com.nprg056.gribki.detail.MushroomDetailViewModel

class MushroomDetailViewModelFactory (
    private val repository: MushroomRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MushroomDetailViewModel(repository) as T
        }
}