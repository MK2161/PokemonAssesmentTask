package com.example.pokemonassesmenttask.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object PokemonListScreen

@Serializable
data class PokemonDetailsScreen(
    val id : String?,
)