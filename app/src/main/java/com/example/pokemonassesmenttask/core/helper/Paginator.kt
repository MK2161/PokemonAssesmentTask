package com.example.pokemonassesmenttask.core.helper

interface Paginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}