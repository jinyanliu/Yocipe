package com.example.yocipe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.utils.Divider

@Composable
fun RecipeCardList(
    recipes: List<Recipe>,
    navigateTo: (Screen) -> Unit
) {
    Column {
        recipes.forEach { recipe ->
            RecipeCard(
                recipe = recipe,
                navigateTo = navigateTo
            )
            Divider()
        }
    }
}