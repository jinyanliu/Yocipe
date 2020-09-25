package com.example.yocipe.ui.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yocipe.R
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.AppDrawer
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.SwipeToRefreshLayout
import com.example.yocipe.ui.home.RecipeCardSimple
import com.example.yocipe.ui.state.UiState
import com.example.yocipe.ui.theme.snackbarAction
import com.example.yocipe.utils.launchUiStateProducer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                currentScreen = Screen.Favorite,
                closeDrawer = {
                    scaffoldState.drawerState.close()
                },
                navigateTo = navigateTo
            )
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.favorite)) },
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
                    HomeScreenErrorAndContent(
                        recipes = recipes,
                        onRefresh = {
                            onRefreshRecipes()
                        },
                        onErrorDismiss = onErrorDismiss,
                        navigateTo = navigateTo,
                        favorites = favorites,
                        onToggleFavorite = onToggleFavorite,
                        modifier = modifier
                    )
                }
            )
        }
    )
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeToRefreshLayout(
            refreshingState = loading,
            onRefresh = onRefresh,
            refreshIndicator = {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier.preferredSize(36.dp).padding(4.dp)
                    )
                }
            },
            content = content
        )
    }
}

@Composable
private fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenErrorAndContent(
    recipes: UiState<List<Recipe>>,
    onRefresh: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Stack(modifier = modifier.fillMaxSize()) {
        if (recipes.data != null) {
            RecipeList(recipes.data, navigateTo, favorites, onToggleFavorite)
        } else if (!recipes.hasError) {
            TextButton(onClick = onRefresh, Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = R.string.tap_to_load_content),
                    textAlign = TextAlign.Center
                )
            }
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
private fun RecipeList(
    recipes: List<Recipe>,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorites: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val recipesSimple = recipes.subList(0, 2)

    ScrollableColumn(modifier) {
        FavoriteRecipeList(recipesSimple, navigateTo, favorites, onToggleFavorites)
    }
}

@Composable
private fun RecipeListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ErrorSnackbar(
    showError: Boolean,
    modifier: Modifier = Modifier,
    onErrorAction: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    launchInComposition(showError) {
        delay(timeMillis = 5000L)
        if (showError) {
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            text = { Text("Can't update latest news") },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = "RETRY",
                        color = MaterialTheme.colors.snackbarAction
                    )
                }
            }
        )
    }
}

@Composable
private fun FavoriteRecipeList(
    recipes: List<Recipe>,
    navigateTo: (Screen) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit
) {
    Column {
        recipes.filter { favorites.contains(it.id) }.forEach { recipe ->
            RecipeCardSimple(
                recipe = recipe,
                navigateTo = navigateTo,
                isFavorite = favorites.contains(recipe.id),
                onToggleFavorite = { onToggleFavorite(recipe.id) }
            )
            RecipeListDivider()
        }
    }
}