package com.example.pokemonassesmenttask.pokemon.data.service

import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon?")
    suspend fun getPokemonList(
        @Query("limit")limit: Int?,
        @Query("offset")offset: Int?
    ): Response<PokemonLists?>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id : String?,
    ) : Response<PokemonDetails?>
}