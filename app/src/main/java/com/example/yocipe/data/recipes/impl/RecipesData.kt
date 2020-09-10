package com.example.yocipe.data.recipes.impl

import com.example.yocipe.R
import com.example.yocipe.model.Recipe

val ingredients_1 = listOf(
        Pair("Blåbär (frysta)", "3 dl"),
        Pair("Banan", "1 styck"),
        Pair("Havregryn", "1 dl"),
        Pair("Havredryck", "3 dl")
)

val ingredients_2 = listOf(
        Pair("Lingon (torrfrysta)", "225 gram"),
        Pair("Yoghurt", "5 dl"),
        Pair("Agavesirap", "3 msk")
)

val instructions_1 = listOf(
        "1. Mixa samman blåbär, banan, havregryn och havredryck.",
        "2. Späd eventuellt med mer havredryck för att få en bra konsistens."
)

val instructions_2 = listOf(
        "1. Lägg alla ingredienser i en blender.",
        "2. Mixa."
)

val recipe1 = Recipe(
        id = "dc523f0ed25c",
        imageId = R.drawable.image_recipe_1,
        name = "Blåbärssmoothie",
        ingredients = ingredients_1,
        instructions = instructions_1
)

val recipe2 = Recipe(
        id = "7446d8dfd7dc",
        imageId = R.drawable.image_recipe_2,
        name = "Lingonlassi",
        ingredients = ingredients_2,
        instructions = instructions_2
)

val recipes: List<Recipe> =
        listOf(
                recipe1,
                recipe2
        )