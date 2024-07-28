package com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pokemonassesmenttask.R
import com.example.pokemonassesmenttask.pokemon.presentation.components.PokemonList

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen(
        pokemonUiList = mutableListOf(),
        isLoading = false,
        onItemClicked = {},
        isPaginating = false,
        isEndReached = false,
        onGetPokemonList = {},
    )
}

@Composable
fun PokemonListScreen(
    pokemonUiList: MutableList<PokemonUiList?>,
    isLoading: Boolean,
    onItemClicked: (String?) -> Unit,
    isPaginating: Boolean,
    isEndReached: Boolean = false,
    onGetPokemonList: () -> Unit,
) {
    val listState = rememberLazyListState()

    val isNeedPaginate =
        remember { derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 2 } }
    if (isNeedPaginate.value && !isEndReached && !isPaginating) {
        onGetPokemonList()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(id = R.color.light_grey_color),
        topBar = {
            Column(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.digi_blue))
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Pokemon",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.white)
                )
            }
        },
        bottomBar = {
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 120.dp, 20.dp),
                thickness = 4.dp,
                color = colorResource(id = R.color.black)
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.light_grey_color))
                        .padding(10.dp)
                ) {
                    if (pokemonUiList.isEmpty()){
                        if (isLoading) {
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
                    } else {
                        LazyColumn( state = listState) {
                            items(pokemonUiList) { pokemon ->
                                PokemonList(
                                    name = pokemon?.name ?: "",
                                    onItemClicked = {
                                        onItemClicked(pokemon?.url)
                                    }
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                            item {
                                if (isLoading) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(vertical = 10.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CircularProgressIndicator(
                                            color = colorResource(id = R.color.digi_blue)
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    )
}

@Composable
fun CircularProgressBar(
    isDisplayed: Boolean,
) {
    if (isDisplayed) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(20.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = colorResource(R.color.digi_blue)
            )
        }
    }
}