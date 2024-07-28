package com.example.pokemonassesmenttask.pokemon.presentation.components

import androidx.annotation.ColorInt
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import coil.compose.rememberAsyncImagePainter
import com.example.pokemonassesmenttask.R
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen.PairStatCount
import com.example.pokemonassesmenttask.pokemon.presentation.screens.pokemonDetailScreen.PokemonUiDetails
import kotlin.math.absoluteValue

@Composable
fun PokemonList(
    name: String,
    onItemClicked :() ->Unit
){
    Surface(
        modifier = Modifier
            .clickable { onItemClicked() }
            .fillMaxWidth(),
        color = Color(name.toHslColor()),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@Preview
@Composable
fun PokemonDetailsPreview() {
    PokemonDetail(null,{}, mutableListOf(),0,{})
}

@Composable
fun PokemonDetail(
    pokemonUiDetails: PokemonUiDetails?,
    onBackPressed :() -> Unit,
    stat : MutableList<PairStatCount?>,
    tabIndex : Int,
    onTabItemClicked : (Int?) ->Unit,
    ) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(top = 40.dp, start = 40.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable {
                        onBackPressed()
                    },
                    tint = Color.White
                )
                Row(modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = pokemonUiDetails?.name ?: "",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "#${pokemonUiDetails?.id}",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(pokemonUiDetails?.sprites?.url),
                    contentDescription = "Back",
                    modifier = Modifier.size(300.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .fillMaxSize()
            ) {
                val tabData = listOf(R.string.about, R.string.basestat)
                Column {
                    TextTabs(
                        tabIndex = tabIndex ,
                        onTabClick = onTabItemClicked,
                        tabData = tabData
                    )
                    Column(modifier = Modifier
                        .padding(40.dp)
                    ) {
                        if (tabIndex == 0){
                            AboutDetail(pokemonUiDetails)
                        } else {
                            LazyColumn {
                                items(stat){ it ->
                                    BaseStat(it)
                                    Spacer(modifier = Modifier.height(30.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextTabs(
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    tabData: List<Int?>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column() {
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.white),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.customTabIndicatorOffset(tabPositions[tabIndex]),
                        color = colorResource(id = R.color.digi_blue),
                    )
                }
            ) {
                tabData.forEachIndexed { index, text ->
                    Tab(selected = tabIndex == index,
                        selectedContentColor = colorResource(id = R.color.digi_blue),
                        unselectedContentColor = colorResource(id = R.color.digi_txt_color),
                        onClick = {
                            onTabClick(index)
                        }, text = {
                            Text(
                                text = stringResource(id = text ?: 0),
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.digi_txt_color)
                            )
                        }
                    )
                }
            }
        }
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 90.dp
    val currentTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabWidth / 2 - indicatorWidth / 2,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
}

@Composable
fun BaseStat(stat : PairStatCount?){
    Column(modifier = Modifier
        .fillMaxWidth(),) {
        Row {
            Text(
                text = stat?.name ?: "",
                fontSize = 14.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${stat?.count?.toInt()} /100",
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.padding(top = 2.dp))
        PercentageRow(stat?.count ?: 0f)
    }
}

@Composable
fun PercentageRow(stat : Float) {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(5.dp)
            .shadow(shape = RoundedCornerShape(10.dp), elevation = 0.dp)
    ) {
        val balanceCount = 100f - stat
        Box(
            modifier = Modifier
                .weight(stat)
                .fillMaxHeight()
                .background(if (stat > 50f) Color.Green else Color.Red)
        )
        Box(
            modifier = Modifier
                .weight(balanceCount)
                .fillMaxHeight()
                .background(Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPercentageRow() {
    AboutDetail(null)
}

@Composable
fun AboutDetail(pokemonUiDetails: PokemonUiDetails?){
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(100.dp)
        ) {
            Text(
                text = "Height",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text =  pokemonUiDetails?.height?.times(10L).toString() + " cm",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(90.dp)
        ) {
            Text(
                text = "Abilities",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = pokemonUiDetails?.abilities?.map { it?.ability?.name }?.joinToString() ?: "",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(100.dp)
        )  {
            Text(
                text = "Weight",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = pokemonUiDetails?.weight?.times(100L).toString() + " lbs",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Breeding",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(100.dp)
        )  {
            Text(
                text = "is Default",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text =if (pokemonUiDetails?.isDefault == true)  "yes" else "no",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(85.dp)
        )  {
            Text(
                text = "Egg Groups",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = pokemonUiDetails?.species?.name ?: "",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}