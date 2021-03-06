package com.example.yocipe.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.RestaurantMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.stringResource
import com.example.yocipe.R
import com.example.yocipe.data.AppContainer
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.ui.favorites.FavoritesScreen
import com.example.yocipe.ui.home.HomeScreen
import com.example.yocipe.ui.recipe.RecipeScreen
import com.example.yocipe.ui.theme.TypographyBody2
import com.example.yocipe.ui.theme.TypographyH6
import com.example.yocipe.ui.theme.YocipeTheme
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen24
import com.example.yocipe.ui.theme.dimen8
import com.example.yocipe.ui.utils.Divider
import com.example.yocipe.ui.utils.Spacer24Vertical
import com.example.yocipe.ui.utils.SpacerFillMax

@Composable
fun YocipeApp(
    appContainer: AppContainer,
    navigationViewModel: NavigationViewModel
) {
    YocipeTheme {
        AppContent(
            navigationViewModel = navigationViewModel,
            recipesRepository = appContainer.recipesRepository
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
                is Screen.Favorites -> FavoritesScreen(
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

@Composable
fun AppDrawer(
    navigateTo: (Screen) -> Unit,
    currentScreen: Screen,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.preferredHeight(dimen24))
        YocipeLogo(Modifier.padding(dimen16))
        Divider(Modifier.padding(horizontal = dimen8))
        DrawerButton(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.screen_home),
            isSelected = currentScreen == Screen.Home,
            action = {
                navigateTo(Screen.Home)
                closeDrawer()
            }
        )
        DrawerButton(
            icon = Icons.Rounded.Favorite,
            label = stringResource(R.string.screen_favorites),
            isSelected = currentScreen == Screen.Favorites,
            action = {
                navigateTo(Screen.Favorites)
                closeDrawer()
            }
        )
        SpacerFillMax()
        DrawerButton(
            icon = Icons.Filled.Settings,
            label = stringResource(id = R.string.developed_by_jinyan_liu),
            isSelected = false,
            action = {}
        )
        Spacer24Vertical()
    }
}

@Composable
private fun YocipeLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            asset = Icons.Rounded.RestaurantMenu,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
        )
        Spacer(Modifier.preferredWidth(dimen8))
        Text(
            text = stringResource(R.string.app_name),
            style = TypographyH6(),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
private fun DrawerButton(
    icon: VectorAsset,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = dimen8, top = dimen8, end = dimen8)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    asset = icon,
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
                )
                Spacer(modifier.preferredWidth(dimen16))
                Text(
                    text = label,
                    style = TypographyBody2(),
                    color = textIconColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}