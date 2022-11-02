package com.example.carapp.carSelect.utils

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}