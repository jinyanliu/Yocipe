package com.example.yocipe.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Icon
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
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yocipe.R
import com.example.yocipe.model.Recipe
import com.example.yocipe.ui.theme.dimen0
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.utils.Divider
import com.example.yocipe.ui.utils.HorizontalSpacer8
import com.example.yocipe.ui.utils.VerticalSpacer8

@Composable
fun RecipeContent(
    recipe: Recipe
) {
    val modifier = Modifier.padding(horizontal = dimen16)
    ScrollableColumn {
        RecipeHeaderImage(recipe)
        Text(text = recipe.name, style = MaterialTheme.typography.h4, modifier = modifier)
        Spacer(Modifier.preferredHeight(dimen16))
        IngredientsList(recipe, modifier)
        Spacer(Modifier.preferredHeight(dimen16))
        InstructionsList(recipe, modifier)
        Spacer(Modifier.preferredHeight(dimen16))
    }
}

@Composable
private fun RecipeHeaderImage(recipe: Recipe) {
    recipe.image?.let { image ->
        val imageModifier = Modifier
            .preferredHeightIn(minHeight = 180.dp, maxHeight = 220.dp)
            .fillMaxWidth()
        Image(asset = image, imageModifier, contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.preferredHeight(dimen16))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun IngredientsList(recipe: Recipe, modifier: Modifier) {
    val recipeServingsNumber = recipe.servings.split(" ")[0].toDouble()
    val recipeServingsUnit = recipe.servings.split(" ")[1]
    var ratio by savedInstanceState { 1.0 }
    var servings by savedInstanceState { recipeServingsNumber }

    var halfButtonEnable by savedInstanceState { true }
    var doubleButtonEnabled by savedInstanceState{true}






    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.ingredients_list),
            style = MaterialTheme.typography.h6
        )
        VerticalSpacer8()


        Row(
            verticalGravity = Alignment.CenterVertically,
        ) {
            Text(
                text = "Quick adjust:",
                style = MaterialTheme.typography.body2
            )
            HorizontalSpacer8()
            TextButton(
                enabled = halfButtonEnable && servings.toInt().rem(2) == 0,
                contentColor = MaterialTheme.colors.onSurface,
                onClick = {
                    servings /= 2
                    ratio = servings / recipeServingsNumber
                },
                border = BorderStroke(0.5.dp, colorResource(R.color.orange700)),
                modifier = Modifier.preferredWidth(52.dp)
            ) {
                Text(
                    text = "X0.5",
                    style = MaterialTheme.typography.button
                )
            }
            HorizontalSpacer8()
            TextButton(
                enabled = doubleButtonEnabled && servings.toInt() <= 50,
                contentColor = MaterialTheme.colors.onSurface,
                onClick = { ratio = 2.0
                    servings *= 2
                    ratio = servings / recipeServingsNumber
                },
                border = BorderStroke(0.5.dp, colorResource(R.color.orange700)),
                modifier = Modifier.preferredWidth(52.dp)
            ) {
                Text(
                    text = "X2.0",
                    style = MaterialTheme.typography.button
                )
            }
            HorizontalSpacer8()
            TextButton(
                contentColor = MaterialTheme.colors.onSurface,
                onClick = {
                    ratio = 1.0
                    servings = recipeServingsNumber
                    halfButtonEnable = true
                    doubleButtonEnabled = true
                },
                border = BorderStroke(0.5.dp, colorResource(R.color.orange700))
            ) {
                Text(
                    text = "Reset",
                    style = MaterialTheme.typography.button
                )
            }
        }

        VerticalSpacer8()

        Row(verticalGravity = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    if (servings >= 2) {
                        servings -= 1
                        ratio = servings / recipeServingsNumber
                    }
                }
            ) {
                Icon(Icons.Filled.ArrowLeft)
            }

            Text(text = formatServings(servings))

            IconButton(
                onClick = {
                    if (servings <= 99) {
                        servings += 1
                        ratio = servings / recipeServingsNumber
                    }
                }
            ) {
                Icon(Icons.Filled.ArrowRight)
            }

            Text(text = recipeServingsUnit)
        }

        VerticalSpacer8()
        recipe.ingredients.forEach { ingredient ->
            SingleIngredient(
                ingredient = ingredient,
                ratio = ratio,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            Divider(Modifier.padding(horizontal = dimen0))
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
private fun InstructionsList(recipe: Recipe, modifier: Modifier) {
    Column(modifier = modifier) {
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
            Divider(Modifier.padding(horizontal = dimen0))
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

fun formatServings(servings: Double): String =
    "%.1f".format(servings).dropLastWhile { it == '0' }.dropLastWhile { it == ',' }