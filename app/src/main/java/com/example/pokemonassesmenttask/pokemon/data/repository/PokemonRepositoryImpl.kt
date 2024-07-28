package com.example.pokemonassesmenttask.pokemon.data.repository

import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.LocalException
import com.example.pokemonassesmenttask.pokemon.data.PokemonRepository
import com.example.pokemonassesmenttask.pokemon.data.repository.mapper.PokemonDetailsMapper
import com.example.pokemonassesmenttask.pokemon.data.repository.mapper.PokemonListMapper
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists
import com.example.pokemonassesmenttask.pokemon.data.service.ApiService

class PokemonRepositoryImpl(private val service: ApiService) : PokemonRepository {

    override suspend fun getPokemonList(limit:Int?, offset:Int?) : CustomResponse<PokemonLists, LocalException> {
        return PokemonListMapper.map(service.getPokemonList(limit,offset))
    }

    override suspend fun getPokemonDetails(id : String?) : CustomResponse<PokemonDetails, LocalException> {
        return PokemonDetailsMapper.map(service.getPokemonDetails(id))
    }
}