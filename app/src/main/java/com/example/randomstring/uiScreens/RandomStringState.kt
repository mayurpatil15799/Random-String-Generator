package com.example.randomstring.uiScreens

import com.example.randomstring.data.RandomStringEntity

data class RandomStringState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDarkTheme: Boolean? = false,
    val list: List<RandomStringEntity> = emptyList()
)