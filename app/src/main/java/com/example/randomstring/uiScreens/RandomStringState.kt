package com.example.randomstring.uiScreens

import com.example.randomstring.data.RandomStringEntity

data class RandomStringState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<RandomStringEntity> = emptyList()
)