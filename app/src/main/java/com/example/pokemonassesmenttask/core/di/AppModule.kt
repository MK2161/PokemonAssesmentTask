package com.example.pokemonassesmenttask.core.di

import com.example.pokemonassesmenttask.pokemon.data.repository.PokemonRepositoryImpl
import com.example.pokemonassesmenttask.core.network.ApiProvider
import com.example.pokemonassesmenttask.pokemon.data.PokemonRepository
import com.example.pokemonassesmenttask.pokemon.data.service.ApiService
import com.example.pokemonassesmenttask.pokemon.domain.PokemonUseCaseProvider
import com.example.pokemonassesmenttask.pokemon.domain.usecase.PokemonUseCase
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen.PokemonDetailsViewModel
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonListScreen.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    private val viewModelModules = module {
        viewModel { PokemonDetailsViewModel(get()) }
        viewModel { PokemonListViewModel(get()) }
    }

    private val repoModules = module {
        single<PokemonRepository> { PokemonRepositoryImpl(get()) }
        single<PokemonUseCaseProvider>{ PokemonUseCase(get()) }
    }

    private val commonModules = module {
        single { ApiProvider.client }
    }

    fun appModules() = viewModelModules + repoModules + commonModules
}