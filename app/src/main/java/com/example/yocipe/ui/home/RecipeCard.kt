package com.example.yocipe.ui.home

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.yocipe.R
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.Screen

@Composable
fun RecipeCardSimple(
    recipe: Recipe,
    navigateTo: (Screen) -> Unit,
    isFavorite: Boolean
) {
    Row(
        modifier = Modifier.clickable(onClick = { navigateTo(Screen.Recipe(recipe.id)) })
            .padding(16.dp)
    ) {
        RecipeImage(recipe, Modifier.padding(end = 16.dp))
        Column(modifier = Modifier.weight(1f)) {
            RecipeTitle(recipe)
            FirstInstruction(recipe)
        }
    }
}

@Composable
fun RecipeImage(recipe: Recipe, modifier: Modifier = Modifier) {
    val image = recipe.image ?: imageResource(id = R.drawable.placeholder)
    Image(
        asset = image,
        modifier = modifier
            .preferredSize(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeTitle(recipe: Recipe) {
    ProvideEmphasis(EmphasisAmbient.current.high) {
        Text(recipe.name, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun FirstInstruction(
    recipe: Recipe
) {
    ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
        Text(
            text = recipe.instructions[0],
            style = MaterialTheme.typography.body2
        )
    }
}