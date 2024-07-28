package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonListScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.pokemonassesmenttask.core.navigation.PokemonDetailsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListRouter(
    navHostController : NavHostController
){
    val viewModel = koinViewModel<PokemonListViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onPokemonList()
    }

    PokemonListScreen(
        pokemonUiList = uiState.pokemonUiList,
        isLoading = uiState.isLoading,
        onItemClicked = { url ->
            navHostController.navigate(
                PokemonDetailsScreen(id = url)
            )
        },
        isPaginating = uiState.isPaginating,
        isEndReached = uiState.isEndReached,
        onGetPokemonList = { viewModel.onGetPokemonList()}
    )
}