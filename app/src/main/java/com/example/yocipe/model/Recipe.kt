package com.example.yocipe.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageAsset

data class Recipe(
    val id: String,
    @DrawableRes val imageId: Int,
    val image: ImageAsset? = null,
    val name: String,
    val ingredients: List<Pair<String, String>>,
    val instructions: List<String>,
    val notes: List<String> = emptyList(),
    val servings: String
)