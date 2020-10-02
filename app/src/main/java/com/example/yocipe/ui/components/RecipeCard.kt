package com.example.yocipe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.example.yocipe.R
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.theme.EmphasisAmbientHigh
import com.example.yocipe.ui.theme.EmphasisAmbientMedium
import com.example.yocipe.ui.theme.TypographyBody2
import com.example.yocipe.ui.theme.TypographySubtitle1
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen40

@Composable
fun RecipeCard(
    recipe: Recipe,
    navigateTo: (Screen) -> Unit
) {
    Row(
        modifier = Modifier.clickable(onClick = {
            navigateTo(Screen.Recipe(recipe.id))
        })
            .padding(dimen16),
        verticalGravity = Alignment.CenterVertically
    ) {
        RecipeImage(recipe)
        Column(modifier = Modifier.weight(1f)) {
            RecipeTitle(recipe)
            FirstInstruction(recipe)
        }
    }
}

@Composable
fun RecipeImage(recipe: Recipe) {
    val image = recipe.image ?: imageResource(id = R.drawable.placeholder)
    Image(
        asset = image,
        modifier = Modifier
            .padding(end = dimen16)
            .preferredSize(dimen40, dimen40)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeTitle(recipe: Recipe) {
    ProvideEmphasis(EmphasisAmbientHigh()) {
        Text(recipe.name, style = TypographySubtitle1())
    }
}

@Composable
fun FirstInstruction(recipe: Recipe) {
    ProvideEmphasis(EmphasisAmbientMedium()) {
        Text(text = recipe.instructions[0], style = TypographyBody2())
    }
}