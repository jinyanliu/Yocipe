package com.example.yocipe.ui.recipe

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.utils.FavoriteButton
import com.example.yocipe.utils.launchUiStateProducer
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(
    recipeId: String,
    recipesRepository: RecipesRepository,
    onBack: () -> Unit
) {
    val (recipe) = launchUiStateProducer(recipesRepository, recipeId) {
        getRecipe(recipeId = recipeId)
    }

    val recipeData = recipe.value.data ?: return

    val favorites by recipesRepository.observeFavorites().collectAsState(setOf())
    val isFavorite = favorites.contains(recipeId)

    val coroutineScope = rememberCoroutineScope()

    RecipeScreen(
        recipe = recipeData,
        onBack = onBack,
        isFavorite = isFavorite,
        onToggleFavorite = {
            coroutineScope.launch { recipesRepository.toggleFavorite(recipeId = recipeId) }
        }
    )
}

@Composable
fun RecipeScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Column {
        Row {
            IconButton(
                onClick = onBack,
                modifier = Modifier.gravity(Alignment.CenterVertically)
            ) {
                Icon(Icons.Filled.ArrowBack)
            }
            Spacer(modifier = Modifier.weight(1f))
            FavoriteButton(isFavorite = isFavorite, onClick = onToggleFavorite)
        }
        RecipeContent(recipe)
    }
}