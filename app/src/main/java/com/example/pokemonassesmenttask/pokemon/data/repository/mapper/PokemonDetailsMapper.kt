package com.example.pokemonassesmenttask.pokemon.data.repository.mapper

import com.example.pokemonassesmenttask.core.helper.ERROR_SERVER
import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.LocalException
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import retrofit2.Response

class PokemonDetailsMapper {
    companion object{
        fun map(pokemonDetailModel: Response<PokemonDetails?>) : CustomResponse<PokemonDetails, LocalException> {
            return if (pokemonDetailModel.isSuccessful && pokemonDetailModel.code() == 200) {
                CustomResponse.Success(
                    PokemonDetails(
                        abilities  = pokemonDetailModel.body()?.abilities,
                        height = pokemonDetailModel.body()?.height,
                        id = pokemonDetailModel.body()?.id,
                        isDefault = pokemonDetailModel.body()?.isDefault,
                        name = pokemonDetailModel.body()?.name,
                        species = pokemonDetailModel.body()?.species,
                        sprites = pokemonDetailModel.body()?.sprites,
                        stats  = pokemonDetailModel.body()?.stats,
                        weight = pokemonDetailModel.body()?.weight,
                    )
                )
            } else CustomResponse.Failure(LocalException(ERROR_SERVER))
        }
    }
}