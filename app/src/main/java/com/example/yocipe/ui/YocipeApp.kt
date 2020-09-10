package com.example.yocipe.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.yocipe.data.AppContainer
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.ui.home.HomeScreen
import com.example.yocipe.ui.recipe.RecipeScreen
import com.example.yocipe.ui.theme.YocipeTheme

@Composable
fun YocipeApp(
        appContainer: AppContainer,
        navigationViewModel:NavigationViewModel
){
    YocipeTheme {
        AppContent(
            navigationViewModel = navigationViewModel,
            recipesRepository= appContainer.recipesRepository
        )
    }
}

@Composable
private fun AppContent(
    navigationViewModel: NavigationViewModel,
    recipesRepository: RecipesRepository
) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(
                    navigateTo = navigationViewModel::navigateTo,
                    recipesRepository = recipesRepository
                )
                is Screen.Recipe -> RecipeScreen(
                    recipeId = screen.recipeId,
                    recipesRepository = recipesRepository,
                    onBack = { navigationViewModel.onBack() }
                )
            }
        }
    }
}