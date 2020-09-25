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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yocipe.R
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
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
        IngredientsList(recipe, ratio)
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
        InstructionsList(recipe)
        Spacer(Modifier.preferredHeight(defaultSpacerSize))
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
        Text(
            text = stringResource(id = R.string.ingredients_list),
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.preferredHeight(8.dp))
        recipe.ingredients.forEach { ingredient ->
            SingleIngredient(
                ingredient = ingredient,
                ratio = ratio,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            RecipeContentListDivider()
        }
    }
}

@Composable
private fun SingleIngredient(
    ingredient: Pair<String, String>,
    ratio: Double,
    modifier: Modifier
) {
    val amountDigit = ingredient.second.split(" ")[0].toDouble() * ratio
    val amountString =
        "%.1f".format(amountDigit).dropLastWhile { it == '0' }.dropLastWhile { it == ',' }
    val measurementUnit = ingredient.second.split(" ")[1]

    Row(modifier) {
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            val textStyle = MaterialTheme.typography.subtitle1
            Text(
                text = ingredient.first,
                style = textStyle,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = "$amountString $measurementUnit",
                style = textStyle
            )
        }
    }
}

@Composable
private fun RecipeContentListDivider() {
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun InstructionsList(recipe: Recipe) {
    Column {
        Text(
            text = stringResource(id = R.string.instructions_list),
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.preferredHeight(8.dp))
        recipe.instructions.forEach { instruction ->
            SingleInstruction(
                instruction = instruction,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            RecipeContentListDivider()
        }
    }
}

@Composable
private fun SingleInstruction(
    instruction: String,
    modifier: Modifier
) {
    Row(modifier) {
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            val textStyle = MaterialTheme.typography.subtitle1
            Text(
                text = instruction,
                style = textStyle
            )
        }
    }
}