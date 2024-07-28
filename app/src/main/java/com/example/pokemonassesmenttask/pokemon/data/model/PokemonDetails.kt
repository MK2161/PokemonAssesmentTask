package com.example.pokemonassesmenttask.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetails(
    @SerializedName("abilities")
    val abilities : ArrayList<Abilities?>?,
    @SerializedName("height")
    val height : Int?,
    @SerializedName("id")
    val id : Int?,
    @SerializedName("is_default")
    val isDefault :Boolean?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("species")
    val species : PairNameUrl?,
    @SerializedName("sprites")
    val sprites : Sprites?,
    @SerializedName("stats")
    val stats : ArrayList<Stats?>?,
    @SerializedName("weight")
    val weight : Int?,
)

data class Stats(
    @SerializedName("base_stat")
    val baseStat : Int?,
    @SerializedName("effort")
    val effort : Int?,
    @SerializedName("stat")
    val stat : PairNameUrl?
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
)

data class Abilities(
    @SerializedName("ability")
    val ability : PairNameUrl?,
    @SerializedName("is_hidden")
    val isHidden : String?,
    @SerializedName("slot")
    val slot : Int?,
)

data class PairNameUrl(
    val name : String?,
    val url : String?,
)