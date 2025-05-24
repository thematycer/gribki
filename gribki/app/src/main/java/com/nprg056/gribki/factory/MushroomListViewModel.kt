package com.nprg056.gribki.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nprg056.gribki.MushroomRepository
import com.nprg056.gribki.mushroomList.MushroomListViewModel

class MushroomListViewModelFactory (
    private val repository: MushroomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MushroomListViewModel(repository) as T
    }
}