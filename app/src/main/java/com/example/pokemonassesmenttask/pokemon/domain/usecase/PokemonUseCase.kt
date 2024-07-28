package com.example.pokemonassesmenttask.pokemon.domain.usecase

import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.LocalException
import com.example.pokemonassesmenttask.pokemon.data.PokemonRepository
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists
import com.example.pokemonassesmenttask.pokemon.domain.PokemonUseCaseProvider

class PokemonUseCase(private val pokemonRepository: PokemonRepository) : PokemonUseCaseProvider {
    override suspend fun getPokemonList(
        limit: Int?,
        offset: Int?
    ): CustomResponse<PokemonLists, LocalException> {
        return pokemonRepository.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonDetails(id: String?): CustomResponse<PokemonDetails, LocalException> {
        return pokemonRepository.getPokemonDetails(id)
    }
}