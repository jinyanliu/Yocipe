package com.example.yocipe.ui.favorites

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.ui.Screen

@Composable
fun FavoriteScreen(
    navigateTo: (Screen) -> Unit,
    recipesRepository: RecipesRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val favorites by recipesRepository.observeFavorites().collectAsState(setOf())
}