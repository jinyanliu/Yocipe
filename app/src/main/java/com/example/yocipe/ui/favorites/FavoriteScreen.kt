package com.example.yocipe.ui.favorites

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yocipe.R
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.AppDrawer
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.components.RecipeCardList
import com.example.yocipe.ui.state.UiState
import com.example.yocipe.ui.utils.FullScreenLoading
import com.example.yocipe.ui.utils.FullScreenMessage
import com.example.yocipe.ui.utils.FullScreenTextButton
import com.example.yocipe.ui.utils.LoadingContent
import com.example.yocipe.utils.ErrorSnackbar
import com.example.yocipe.utils.launchUiStateProducer

@Composable
fun FavoriteScreen(
    navigateTo: (Screen) -> Unit,
    recipesRepository: RecipesRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val (recipeUiState, refreshRecipe, clearError) = launchUiStateProducer(recipesRepository) {
        getRecipes()
    }

    val favorites by recipesRepository.observeFavorites().collectAsState(setOf())

    FavoriteScreen(
        recipes = recipeUiState.value,
        favorites = favorites,
        onRefreshRecipes = refreshRecipe,
        onErrorDismiss = clearError,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun FavoriteScreen(
    recipes: UiState<List<Recipe>>,
    favorites: Set<String>,
    onRefreshRecipes: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            AppDrawer(
                currentScreen = Screen.Favorite,
                closeDrawer = {
                    scaffoldState.drawerState.close()
                },
                navigateTo = navigateTo
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.screen_favorite)) },
                navigationIcon = {
                    IconButton(onClick = { scaffoldState.drawerState.open() }) {
                        Icon(Icons.Rounded.RestaurantMenu)
                    }
                }
            )
        },
        bodyContent = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            LoadingContent(
                empty = recipes.initialLoad,
                emptyContent = { FullScreenLoading() },
                loading = recipes.loading,
                onRefresh = onRefreshRecipes,
                content = {
                    FavoriteScreenErrorAndContent(
                        recipes = recipes,
                        onRefresh = {
                            onRefreshRecipes()
                        },
                        onErrorDismiss = onErrorDismiss,
                        navigateTo = navigateTo,
                        favorites = favorites,
                        modifier = modifier
                    )
                }
            )
        }
    )
}

@Composable
private fun FavoriteScreenErrorAndContent(
    recipes: UiState<List<Recipe>>,
    onRefresh: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    modifier: Modifier = Modifier
) {
    Stack(modifier = modifier.fillMaxSize()) {
        if (recipes.data != null) {
            val favoriteRecipes = recipes.data.filter { favorites.contains(it.id) }
            if (favoriteRecipes.isNotEmpty()) {
                FavoriteScreenContent(favoriteRecipes, navigateTo)
            } else {
                FullScreenMessage(
                    mainMessage = stringResource(id = R.string.empty_favorite_main_message),
                    instructionMessage = stringResource(id = R.string.empty_favorite_instruction_message),
                    textButtonMessage = stringResource(id = R.string.empty_favorite_text_button_message)
                ) {
                    navigateTo(Screen.Home)
                }
            }
        } else if (!recipes.hasError) {
            FullScreenTextButton(stringResource(id = R.string.tap_to_load_content), onRefresh)
        }
        ErrorSnackbar(
            showError = recipes.hasError,
            onErrorAction = onRefresh,
            onDismiss = onErrorDismiss,
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun FavoriteScreenContent(
    favoriteRecipes: List<Recipe>,
    navigateTo: (Screen) -> Unit
) {
    ScrollableColumn {
        RecipeCardList(favoriteRecipes, navigateTo)
    }
}