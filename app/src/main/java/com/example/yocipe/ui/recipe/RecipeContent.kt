package com.example.yocipe.ui.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.Divider
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.yocipe.model.Recipe

private val defaultSpacerSize = 16.dp

@Composable
fun RecipeContent(
    recipe: Recipe,
    modifier: Modifier,
    ratio: Double
) {
    ScrollableColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize)
    ) {
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
        RecipeHeaderImage(recipe)
        Text(text = recipe.name, style = MaterialTheme.typography.h4)
        Spacer(Modifier.preferredHeight(8.dp))
        IngredientsList(recipe, ratio)
        Spacer(Modifier.preferredHeight(24.dp))
        //InstructionsList(recipe.instructions)
        Spacer(Modifier.preferredHeight(48.dp))
    }
}

@Composable
private fun RecipeHeaderImage(recipe: Recipe) {
    recipe.image?.let { image ->
        val imageModifier = Modifier
            .preferredHeightIn(minHeight = 180.dp, maxHeight = 220.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
        Image(asset = image, imageModifier, contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.preferredHeight(defaultSpacerSize))
    }
}

@Composable
private fun IngredientsList(recipe: Recipe, ratio: Double) {
    Column {
        recipe.ingredients.forEach { ingredient ->
            SingleIngredient(
                ingredient = ingredient,
                ratio = ratio,
                modifier = Modifier.padding(top = 4.dp)
            )
            IngredientListDivider()
        }
    }
}

@Composable
private fun SingleIngredient(
    ingredient: Pair<String, String>,
    ratio: Double,
    modifier: Modifier
) {
    Row(modifier) {
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            val textStyle = MaterialTheme.typography.body1
            Text(
                text = ingredient.first,
                style = textStyle,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = ingredient.second,
                style = textStyle
            )
        }
    }
}

@Composable
private fun IngredientListDivider() {
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}