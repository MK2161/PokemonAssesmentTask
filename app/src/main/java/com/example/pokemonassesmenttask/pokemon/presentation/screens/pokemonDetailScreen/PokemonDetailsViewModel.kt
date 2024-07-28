package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.helper.ERROR_SERVER
import com.example.pokemonassesmenttask.pokemon.data.model.PairNameUrl
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonDetails
import com.example.pokemonassesmenttask.pokemon.data.model.Stats
import com.example.pokemonassesmenttask.pokemon.domain.PokemonUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonUseCaseProvider : PokemonUseCaseProvider
) : ViewModel(){
    private val viewModelState = MutableStateFlow(
        PokemonDetailViewModelState(isLoading = true)
    )
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())


    fun onPokemonList(id: String?){
        viewModelScope.launch {
            when (val response = pokemonUseCaseProvider.getPokemonDetails(id)) {
                is CustomResponse.Success ->{
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            pokemonUiDetails = pokemonDetails(response.data),
                            stat = convertUiStats(response.data.stats),
                        )
                    }
                }
                is CustomResponse.Failure ->{
                    throw Exception(ERROR_SERVER)
                }
            }
        }
    }

    fun onTabItemClicked(index :Int?){
        viewModelState.update {
            it.copy(
                isLoading = false,
                tabIndex = index,
            )
        }
    }

    private fun convertUiStats(states : ArrayList<Stats?>?) : MutableList<PairStatCount?>{
        val pairStatCount : MutableList<PairStatCount?> = mutableListOf()
        states?.forEach { stat ->
            when(stat?.stat?.name){
                "hp" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "HP",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
                "attack" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "Attack",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
                "defense" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "Defense",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
                "special-attack" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "Sp.Atk",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
                "special-defense" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "Sp.Def",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
                "speed" -> {
                    pairStatCount.add(
                        PairStatCount(
                            name = "Speed",
                            count = if((stat.baseStat?.toFloat() ?: 1f) < 99f)stat.baseStat?.toFloat() else 99f
                        )
                    )
                }
            }
        }
        return pairStatCount
    }
    private fun pokemonDetails(pokemonDetails : PokemonDetails) : PokemonUiDetails?{
        return PokemonUiDetails(
            abilities = pokemonDetails.abilities?.map { it ->
                UiAbilities(
                    ability = PairNameUrl(name = it?.ability?.name, url = it?.ability?.url),
                    isHidden = it?.isHidden,
                    slot = it?.slot
                )
            }?.toMutableList() ?: mutableListOf(),
            height = pokemonDetails.height,
            weight = pokemonDetails.weight,
            id = pokemonDetails.id,
            isDefault = pokemonDetails.isDefault,
            name = pokemonDetails.name,
            species = UiSpecies(name = pokemonDetails.species?.name, url = pokemonDetails.species?.url),
            sprites = PokemonImage(url = pokemonDetails.sprites?.frontDefault),
            stats = convertUiStats(pokemonDetails.stats)
        )
    }
}
data class PokemonDetailViewModelState(
    val isLoading: Boolean = false,
    val pokemonUiDetails : PokemonUiDetails? = null,
    val stat :  MutableList<PairStatCount?> = mutableListOf(),
    val tabIndex : Int? = 0,
) {
    fun toUiState() = PokemonDetailUiState(
        isLoading = isLoading,
        pokemonUiDetails = pokemonUiDetails,
        stat = stat,
        tabIndex = tabIndex
    )
}

data class PokemonDetailUiState(
    val isLoading: Boolean,
    val pokemonUiDetails : PokemonUiDetails?,
    val stat :  MutableList<PairStatCount?>,
    val tabIndex : Int?,
)

data class PokemonUiDetails(
    val abilities : MutableList<UiAbilities?>,
    val height : Int?,
    val id : Int?,
    val isDefault :Boolean?,
    val name : String?,
    val species : UiSpecies?,
    val stats : MutableList<PairStatCount?>,
    val sprites : PokemonImage?,
    val weight : Int?,
)
data class UiAbilities(
    val ability : PairNameUrl?,
    val isHidden : String?,
    val slot : Int?,
)
data class PokemonImage(
    val url: String?,
)

data class UiSpecies(
    val name: String?,
    val url : String?,
)

data class PairStatCount(
    val name : String?,
    val count: Float?,
)