package com.example.pokemonassesmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

import com.example.pokemonassesmenttask.core.navigation.PokemonDetailsScreen
import com.example.pokemonassesmenttask.core.navigation.PokemonListScreen
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen.PokemonDetailsRouter
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonListScreen.PokemonListRouter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigation()
        }
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = PokemonListScreen){
        composable<PokemonListScreen>{
            PokemonListRouter(navController)
        }
        composable<PokemonDetailsScreen>{
            val args = it.toRoute<PokemonDetailsScreen>()
            PokemonDetailsRouter(navHostController = navController,args.id)
        }
    }
}

