package com.example.yocipe.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageAsset
import com.example.yocipe.data.recipes.impl.UnitSingular

data class Recipe(
    val id: String,
    @DrawableRes val imageId: Int,
    val image: ImageAsset? = null,
    val name: String,
    val ingredients: List<Pair<String, String>>,
    val instructions: List<String>,
    val notes: List<String> = emptyList(),
    val servings: Int,
    val servingsUnitSingular: UnitSingular,
    val servingsUnitPlural: String,
)