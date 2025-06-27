package com.example.randomstring.uiScreens

sealed class RandomStringIntent {
    data class Generate(val maxLength: Int) : RandomStringIntent()
    data class DeleteItem(val id: Int) : RandomStringIntent()
    object DeleteAll : RandomStringIntent()
    object LoadAll : RandomStringIntent()
}
