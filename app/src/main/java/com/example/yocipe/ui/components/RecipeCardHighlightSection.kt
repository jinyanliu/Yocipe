package com.example.yocipe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.yocipe.R
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.Screen
import com.example.yocipe.ui.theme.EmphasisAmbientHigh
import com.example.yocipe.ui.theme.EmphasisAmbientMedium
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen180
import com.example.yocipe.ui.theme.dimen220
import com.example.yocipe.ui.theme.TypographyBody2
import com.example.yocipe.ui.theme.TypographyH6
import com.example.yocipe.ui.theme.TypographySubtitle1
import com.example.yocipe.ui.utils.Divider
import com.example.yocipe.ui.utils.Spacer16Vertical
import com.example.yocipe.utils.yocipeToUppercase

@Composable
fun RecipeCardHighlightSection(recipe: Recipe, navigateTo: (Screen) -> Unit) {
    ProvideEmphasis(emphasis = EmphasisAmbientHigh()) {
        Text(
            modifier = Modifier.padding(start = dimen16, top = dimen16, end = dimen16),
            text = "Top recipes for you",
            style = TypographySubtitle1()
        )
    }
    RecipeCardHighlight(
        recipe = recipe,
        modifier = Modifier.clickable(onClick = { navigateTo(Screen.Recipe(recipe.id)) })
    )
    Divider()
}

@Composable
private fun RecipeCardHighlight(recipe: Recipe, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(dimen16)) {
        recipe.image?.let { image ->
            val imageModifier = Modifier
                .preferredHeightIn(minHeight = dimen180, maxHeight = dimen220)
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
            Image(image, modifier = imageModifier, contentScale = ContentScale.Crop)
        }
        Spacer16Vertical()
        ProvideEmphasis(EmphasisAmbientHigh()) {
            Text(
                text = recipe.name,
                style = TypographyH6()
            )
            Text(
                text = recipe.instructions[0],
                style = TypographyBody2()
            )
        }
        ProvideEmphasis(EmphasisAmbientMedium()) {
            Text(
                text = stringResource(id = R.string.read_more).yocipeToUppercase(),
                style = TypographyBody2()
            )
        }
    }
}