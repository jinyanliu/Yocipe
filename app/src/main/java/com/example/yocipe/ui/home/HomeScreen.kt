package com.example.yocipe.ui.home

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.ui.Screen

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    recipesRepository: RecipesRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
){

}