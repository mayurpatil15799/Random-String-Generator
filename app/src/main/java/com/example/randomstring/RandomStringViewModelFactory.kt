package com.example.randomstring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomstring.repository.RandomStringRepository
import com.example.randomstring.uiScreens.RandomStringViewModel

class RandomStringViewModelFactory(
    private val repository: RandomStringRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomStringViewModel::class.java)) {
            return RandomStringViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}