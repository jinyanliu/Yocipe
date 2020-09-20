package com.example.yocipe.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.yocipe.model.Recipe

@Composable
fun RecipeCardTop(recipe: Recipe, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        recipe.image?.let { image ->
            val imageModifier = Modifier
                .preferredHeightIn(minHeight = 180.dp, maxHeight = 220.dp)
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
            Image(image, modifier = imageModifier, contentScale = ContentScale.Crop)
        }
        Spacer(Modifier.preferredHeight(16.dp))

        val emphasisLevels = EmphasisAmbient.current
        ProvideEmphasis(emphasisLevels.high) {
            Text(
                text = recipe.name,
                style = typography.h6
            )
            Text(
                text = recipe.instructions[0],
                style = typography.body2
            )
        }
    }
}