package com.alxnns1.planets.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alxnns1.planets.PlanetsViewModel
import com.alxnns1.utils.ui.ErrorLayout
import com.alxnns1.utils.ui.LoadingLayout
import com.alxnns1.utils.ResultOf
import com.alxnns1.utils.ui.SearchBar

const val PLANET_SCREEN_ROUTE = "planets_route"

@Composable
fun PlanetsScreen(onClick: (String) -> Unit) {
    val viewModel: PlanetsViewModel = hiltViewModel()
    val planetsState by viewModel.planetsState.collectAsState()

    Column {
        SearchBar { viewModel.getPlanets(it) }
        planetsState.let { result ->
            when (result) {
                is ResultOf.Success -> PlanetsSuccessLayout(result.value) { onClick(it) }
                is ResultOf.Error -> ErrorLayout()
                is ResultOf.Loading -> LoadingLayout()
                is ResultOf.Initial -> viewModel.getPlanets()
            }
        }
    }
}

@Composable
fun PlanetsSuccessLayout(planets: List<String>, onClick: (String) -> Unit) {
    LazyColumn {
        items(items = planets) {
            Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(
                    modifier = Modifier
                        .clickable { onClick(it) }
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = it
                )
            }
        }
    }
}