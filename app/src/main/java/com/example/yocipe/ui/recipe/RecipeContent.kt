package com.example.yocipe.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.foundation.layout.preferredWidth
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
import com.example.yocipe.ui.theme.EmphasisAmbientMedium
import com.example.yocipe.ui.theme.TypographyBody2
import com.example.yocipe.ui.theme.TypographyButton
import com.example.yocipe.ui.theme.TypographyH4
import com.example.yocipe.ui.theme.TypographyH6
import com.example.yocipe.ui.theme.TypographySubtitle1
import com.example.yocipe.ui.theme.dimen0
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen180
import com.example.yocipe.ui.theme.dimen220
import com.example.yocipe.ui.theme.dimen4
import com.example.yocipe.ui.theme.dimen52
import com.example.yocipe.ui.utils.Divider
import com.example.yocipe.ui.utils.Spacer16Vertical
import com.example.yocipe.ui.utils.Spacer8Horizontal
import com.example.yocipe.ui.utils.Spacer8Vertical

@Composable
fun RecipeContent(
    recipe: Recipe
) {
    val modifier = Modifier.padding(horizontal = dimen16)
    ScrollableColumn {
        RecipeHeaderImage(recipe)
        Spacer16Vertical()
        Text(text = recipe.name, style = TypographyH4(), modifier = modifier)
        Spacer16Vertical()
        Ingredients(recipe, modifier)
        Spacer16Vertical()
        Instructions(recipe, modifier)
        Spacer16Vertical()
    }
}

@Composable
private fun RecipeHeaderImage(recipe: Recipe) {
    recipe.image?.let { image ->
        val imageModifier = Modifier
            .preferredHeightIn(minHeight = dimen180, maxHeight = dimen220)
            .fillMaxWidth()
        Image(asset = image, imageModifier, contentScale = ContentScale.Crop)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Ingredients(recipe: Recipe, modifier: Modifier) {
    val recipeServingsNumber = recipe.servings.split(" ")[0].toDouble()
    val recipeServingsUnit = recipe.servings.split(" ")[1]

    var ratio by savedInstanceState { 1.0 }
    var servings by savedInstanceState { recipeServingsNumber }

    var halfButtonEnable by savedInstanceState { true }
    var doubleButtonEnabled by savedInstanceState { true }

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.ingredients_list),
            style = TypographyH6()
        )
        Spacer8Vertical()

        /* ************************************************************************************* *
         *                            Quick adjust: X0.5 X2.0 Reset                              *
         * ************************************************************************************* */
        Row(
            verticalGravity = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.quick_adjust),
                style = TypographyBody2()
            )
            Spacer8Horizontal()
            TextButton(
                enabled = halfButtonEnable && servings.toInt().rem(2) == 0,
                contentColor = MaterialTheme.colors.onSurface,
                onClick = {
                    servings /= 2
                    ratio = servings / recipeServingsNumber
                },
                border = BorderStroke(0.5.dp, colorResource(R.color.orange700)),
                modifier = Modifier.preferredWidth(dimen52)
            ) {
                Text(
                    text = stringResource(id = R.string.half_button),
                    style = TypographyButton()
                )
            }
            Spacer8Horizontal()
            TextButton(
                enabled = doubleButtonEnabled && servings.toInt() <= 50,
                contentColor = MaterialTheme.colors.onSurface,
                onClick = {
                    servings *= 2
                    ratio = servings / recipeServingsNumber
                },
                border = BorderStroke(0.5.dp, colorResource(R.color.orange700)),
                modifier = Modifier.preferredWidth(dimen52)
            ) {
                Text(
                    text = stringResource(id = R.string.double_button),
                    style = TypographyButton()
                )
            }
            Spacer8Horizontal()
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
                    text = stringResource(id = R.string.reset),
                    style = TypographyButton()
                )
            }
        }
        Spacer8Vertical()

        /* ************************************************************************************* *
         *                                    < 4 > pizzor                                       *
         * ************************************************************************************* */
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
            Text(text = formatDisplayNumber(servings))
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
        Spacer8Vertical()

        recipe.ingredients.forEach { ingredient ->
            SingleIngredient(
                ingredient = ingredient,
                ratio = ratio,
                modifier = Modifier.padding(top = dimen4, bottom = dimen4)
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
    val amountString = formatDisplayNumber(amountDigit)
    val measurementUnit = ingredient.second.split(" ")[1]

    Row(modifier) {
        ProvideEmphasis(EmphasisAmbientMedium()) {
            Text(
                text = ingredient.first,
                style = TypographySubtitle1(),
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = "$amountString $measurementUnit",
                style = TypographySubtitle1()
            )
        }
    }
}

@Composable
private fun Instructions(recipe: Recipe, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.instructions_list),
            style = TypographyH6()
        )
        Spacer8Vertical()
        recipe.instructions.forEach { instruction ->
            SingleInstruction(
                instruction = instruction,
                modifier = Modifier.padding(top = dimen4, bottom = dimen4)
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
        ProvideEmphasis(EmphasisAmbientMedium()) {
            Text(
                text = instruction,
                style = TypographySubtitle1()
            )
        }
    }
}

fun formatDisplayNumber(number: Double): String =
    "%.1f".format(number).dropLastWhile { it == '0' }.dropLastWhile { it == ',' || it == '.' }