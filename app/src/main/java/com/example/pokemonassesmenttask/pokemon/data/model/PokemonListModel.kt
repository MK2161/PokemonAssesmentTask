package com.example.pokemonassesmenttask.pokemon.data.model

data class PokemonLists (
    val count: Int?,
    val results: ArrayList<PokemonImageModel?>?,
)

data class PokemonImageModel (
    val name: String?,
    val url: String?,
)