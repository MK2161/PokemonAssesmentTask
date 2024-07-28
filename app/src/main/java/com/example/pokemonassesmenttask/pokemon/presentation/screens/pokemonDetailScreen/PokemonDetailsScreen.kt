package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pokemonassesmenttask.R
import com.example.pokemonassesmenttask.pokemon.presentation.components.PokemonDetail


@Composable
fun PokemonDetailsScreen(
    pokemonUiDetails: PokemonUiDetails?,
    isLoading : Boolean?,
    onBackPressed : () -> Unit,
    stat : MutableList<PairStatCount?>,
    onTabItemClicked : (Int?) -> Unit,
    tabIndex : Int,
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(id = R.color.light_grey_color),
        bottomBar = {
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 120.dp,10.dp),
                thickness = 4.dp,
                color = colorResource(id = R.color.black)
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Column(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.light_grey_color))
                ) {
                    PokemonDetail(
                        pokemonUiDetails = pokemonUiDetails,
                        onBackPressed =onBackPressed,
                        stat = stat,
                        tabIndex = tabIndex,
                        onTabItemClicked = onTabItemClicked,
                    )
                }
                Column {
                    if (isLoading == true) {
                        Dialog(
                            onDismissRequest = {}
                        ) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(20.dp),
                                    color = colorResource(id = R.color.digi_blue),
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}