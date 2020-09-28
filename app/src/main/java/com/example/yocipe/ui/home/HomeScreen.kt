package com.example.yocipe.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
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
import androidx.compose.runtime.launchInComposition
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
import com.example.yocipe.ui.components.RecipeCardList
import com.example.yocipe.ui.state.UiState
import com.example.yocipe.ui.theme.snackbarAction
import com.example.yocipe.ui.utils.FullScreenLoading
import com.example.yocipe.ui.utils.Divider
import com.example.yocipe.utils.launchUiStateProducer
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navigateTo: (Screen) -> Unit,
    recipesRepository: RecipesRepository,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val (recipeUiState, refreshRecipe, clearError) = launchUiStateProducer(recipesRepository) {
        getRecipes()
    }

    HomeScreen(
        recipes = recipeUiState.value,
        onRefreshRecipes = refreshRecipe,
        onErrorDismiss = clearError,
        navigateTo = navigateTo,
        scaffoldState = scaffoldState
    )
}

@Composable
fun HomeScreen(
    recipes: UiState<List<Recipe>>,
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
private fun HomeScreenErrorAndContent(
    recipes: UiState<List<Recipe>>,
    onRefresh: () -> Unit,
    onErrorDismiss: () -> Unit,
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Stack(modifier = modifier.fillMaxSize()) {
        if (recipes.data != null) {
            RecipeList(recipes.data, navigateTo)
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
    modifier: Modifier = Modifier
) {
    val recipeTop = recipes[0]
    val recipesSimple = recipes.subList(0, 2)

    ScrollableColumn(modifier) {
        RecipeListTopSection(recipeTop, navigateTo)
        RecipeCardList(recipesSimple, navigateTo)
    }
}

@Composable
private fun RecipeListTopSection(recipe: Recipe, navigateTo: (Screen) -> Unit) {
    ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = "Top recipes for you",
            style = MaterialTheme.typography.subtitle1
        )
    }
    RecipeCardTop(
        recipe = recipe,
        modifier = Modifier.clickable(onClick = { navigateTo(Screen.Recipe(recipe.id)) })
    )
    Divider()
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
            text = { Text("Can't update recipes") },
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

