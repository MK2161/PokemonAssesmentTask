package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonassesmenttask.core.handler.CustomResponse
import com.example.pokemonassesmenttask.core.handler.PagingSource
import com.example.pokemonassesmenttask.core.helper.ERROR_SERVER
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonImageModel
import com.example.pokemonassesmenttask.pokemon.data.model.PokemonLists
import com.example.pokemonassesmenttask.pokemon.domain.PokemonUseCaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonListViewModel(
    private val pokemonUseCaseProvider : PokemonUseCaseProvider
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        PokemonListViewModelState(isLoading = true)
    )
    private var currentPage: Int = 1
    private var totalPages: Int = 1

    private var pokemonList: MutableList<PokemonUiList?> = mutableListOf()

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())

    private val pokemonListPagination = PagingSource(
        initialKey = currentPage,
        onLoadUpdated = { loadState ->
            viewModelState.update { it.copy(isPaginating = if (!viewModelState.value.isLoading) loadState else false) }
        },
        onRequest = { nextPage ->
            getChannelMedia(
                limit = 20,
                offset = nextPage * 20
            )
        },
        getNextKey = {
            currentPage + 1
        },
        onError = {
            viewModelState.update { it.copy(isLoading = false) }
        },
        onSuccess = { items, newKey ->
            currentPage = newKey
            pokemonList.addAll(getPokemonList(items?.results))
            totalPages = items?.count ?: 0
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    isEndReached = items?.results?.isEmpty() ?: false,
                    pokemonUiList = pokemonList
                )
            }
        }
    )

    private suspend fun getChannelMedia(
        offset: Int?,
        limit: Int?,
    ): Result<PokemonLists?> {
        return withContext(Dispatchers.IO) {
            when (val response = pokemonUseCaseProvider.getPokemonList(limit,offset)) {
                is CustomResponse.Success ->{
                    Result.success(
                        PokemonLists(
                            count = response.data.count,
                            results = response.data.results
                        )
                    )
                }
                is CustomResponse.Failure ->{
                    throw Exception(ERROR_SERVER)
                }
            }
        }
    }


    fun onPokemonList(){
        viewModelScope.launch {
            when (val response = pokemonUseCaseProvider.getPokemonList(null,null)) {
                is CustomResponse.Success ->{
                    pokemonList = getPokemonList(response.data.results)
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            pokemonUiList = pokemonList,
                        )
                    }
                }
                is CustomResponse.Failure ->{
                    throw Exception(ERROR_SERVER)
                }
            }
        }
    }

    fun onGetPokemonList(){
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            if (currentPage <= totalPages) {
                viewModelState.update { it.copy(isPaginating = !viewModelState.value.isLoading) }
                pokemonListPagination.loadNextItems()
            }
        }
    }

    private fun getPokemonList(pokemonModel :  ArrayList<PokemonImageModel?>?) : MutableList<PokemonUiList?>{
        return pokemonModel?.map { pokemon ->
            PokemonUiList(
                name = pokemon?.name,
                url = pokemon?.url
            )
        }?.toMutableList() ?: mutableListOf()
    }

    override fun onCleared() {
        super.onCleared()
        pokemonListPagination.reset()
        currentPage = 1
        totalPages = 1
    }
}



data class PokemonListViewModelState(
    val isLoading: Boolean = false,
    val pokemonUiList : MutableList<PokemonUiList?> = mutableListOf(),
    val isPaginating: Boolean = false,
    var isEndReached: Boolean = false,
    ) {
    fun toUiState() = PokemonListUiState(
        isLoading = isLoading,
        pokemonUiList = pokemonUiList,
        isPaginating = isPaginating,
        isEndReached = isEndReached,
    )
}

data class PokemonListUiState(
    val isLoading: Boolean,
    val pokemonUiList : MutableList<PokemonUiList?>,
    val isPaginating: Boolean,
    var isEndReached: Boolean = false,
    )

data class PokemonUiList(
    val url : String?,
    val name : String?,
)