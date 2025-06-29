package com.example.randomstring.uiScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstring.repository.RandomStringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomStringViewModel(
    private val repository: RandomStringRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RandomStringState())
    val state: StateFlow<RandomStringState> = _state

    private val _theme = MutableStateFlow(false)
    val theme: StateFlow<Boolean> = _theme

    fun handleIntent(intent: RandomStringIntent) {
        when (intent) {
            is RandomStringIntent.Generate -> generateString(intent.maxLength)
            is RandomStringIntent.DeleteItem -> deleteItem(intent.id)
            is RandomStringIntent.DeleteAll -> deleteAll()
            is RandomStringIntent.LoadAll -> observeAll()
            RandomStringIntent.ToggleTheme -> toggleTheme()
        }
    }

    private fun observeAll() {
        viewModelScope.launch {
            repository.allStrings.collectLatest { list ->
                _state.value = _state.value.copy(list = list, isLoading = false, error = null)
            }
        }
    }

    private fun generateString(maxLength: Int) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = repository.generateRandomString(maxLength)
            result.onFailure {
                _state.value = _state.value.copy(isLoading = false, error = it.message)
            }
        }
    }

    private fun deleteItem(id: Int) {
        viewModelScope.launch {
            val item = _state.value.list.find { it.id == id } ?: return@launch
            repository.deleteStringById(item)
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAllStrings()
        }
    }

    fun toggleTheme(){
        viewModelScope.launch {
            _theme.value = !_theme.value
            _state.value = _state.value.copy(isDarkTheme = _theme.value)
        }
    }

}