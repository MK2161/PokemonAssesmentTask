package com.example.pokemonassesmenttask.pokemon.data.repository.mapper

import com.example.pokemonassesmenttask.core.helper.ERROR_SERVER
import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.LocalException
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonImageModel
import retrofit2.Response

class PokemonListMapper {
    companion object {
        fun map(pokemonModel: Response<PokemonLists?>) : CustomResponse<PokemonLists, LocalException> {
            return if (pokemonModel.isSuccessful && pokemonModel.code() == 200) {
                CustomResponse.Success(
                    PokemonLists(
                        pokemonModel.body()?.count,
                        ArrayList(pokemonModel.body()?.results?.map { imageModel ->
                            PokemonImageModel(
                                imageModel?.name,
                                imageModel?.url,
                            )
                        } ?: listOf())
                    )
                )
            } else CustomResponse.Failure(LocalException(ERROR_SERVER))
        }
    }
}