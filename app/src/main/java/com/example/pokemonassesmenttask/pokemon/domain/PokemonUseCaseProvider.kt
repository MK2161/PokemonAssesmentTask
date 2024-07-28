package com.example.pokemonassesmenttask.pokemon.domain

import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.LocalException
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists

interface PokemonUseCaseProvider {
    suspend fun getPokemonList(limit:Int?,offset:Int?) : CustomResponse<PokemonLists, LocalException>

    suspend fun getPokemonDetails(id : String?,) : CustomResponse<PokemonDetails, LocalException>
}