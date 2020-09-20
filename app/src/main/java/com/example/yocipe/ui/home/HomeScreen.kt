package com.example.yocipe.ui.home

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.example.yocipe.R
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.AppDrawer
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.state.UiState
import com.example.yocipe.utils.launchUiStateProducer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    recipesRepository: RecipesRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val (recipeUiState, refreshRecipe, clearError) = launchUiStateProducer(recipesRepository) {
        getRecipes()
    }

    val favorites by recipesRepository.observeFavorites().collectAsState(setOf())

    val coroutineScope = rememberCoroutineScope()

    HomeScreen(
        recipes = recipeUiState.value,
        favorites = favorites,
        onToggleFavorite = {
            coroutineScope.launch { recipesRepository.toggleFavorite(it) }
        },
        onRefreshRecipes = refreshRecipe,
        onErrorDismiss = clearError,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )
}

@Composable
fun HomeScreen(
    recipes: UiState<List<Recipe>>,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onRefreshRecipes: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Home,
                closeDrawer = {
                    scaffoldState.drawerState.close()
                },
                navigateTo = navigateTo
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState.open() }) {
                        Icon(Icons.Rounded.RestaurantMenu)
                    }
                }
            )
        },
        bodyContent = {

        }
    )
}