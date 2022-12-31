package com.example.mymvvm.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RoomViewModelFactory(private val repository: RoomRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RoomViewModel::class.java)){
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}