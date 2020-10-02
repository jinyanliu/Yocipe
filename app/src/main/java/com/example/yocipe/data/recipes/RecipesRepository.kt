package com.example.yocipe.data.recipes

import com.example.yocipe.data.Result
import com.example.yocipe.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun getRecipe(recipeId: String): Result<Recipe>
    suspend fun getRecipes(): Result<List<Recipe>>
    fun observeFavorites(): Flow<Set<String>>
    suspend fun toggleFavorite(recipeId: String)
}