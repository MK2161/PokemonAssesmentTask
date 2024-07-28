package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.pokemonassesmenttask.core.helper.extractIdFromUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailsRouter(
    navHostController : NavHostController,
    id : String?,
){
    val viewModel = koinViewModel<PokemonDetailsViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        val url = id?.extractIdFromUrl()
        viewModel.onPokemonList(url)
    }

    PokemonDetailsScreen(
        pokemonUiDetails = uiState.pokemonUiDetails,
        isLoading = uiState.isLoading,
        onBackPressed = {
            navHostController.navigateUp()
        },
        stat = uiState.stat,
        tabIndex = uiState.tabIndex ?: 0,
        onTabItemClicked = { viewModel.onTabItemClicked(it)}
    )
}