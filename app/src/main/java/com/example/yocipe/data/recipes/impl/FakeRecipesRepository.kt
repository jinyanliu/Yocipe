package com.example.yocipe.data.recipes.impl

import android.content.res.Resources
import androidx.compose.ui.graphics.imageFromResource
import com.example.yocipe.data.Result
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.model.Recipe
import com.example.yocipe.utils.addOrRemove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class FakeRecipesRepository(
    private val resources: Resources
) : RecipesRepository {

    private val recipesWithResources: List<Recipe> by lazy {
        recipes.map {
            it.copy(
                image = imageFromResource(resources, it.imageId)
            )
        }
    }

    private val favorites = MutableStateFlow<Set<String>>(setOf())

    private val mutex = Mutex()

    override suspend fun getRecipe(recipeId: String): Result<Recipe> {
        return withContext(Dispatchers.IO) {
            val recipe = recipesWithResources.find { it.id == recipeId }
            if (recipe == null) {
                Result.Error(IllegalArgumentException("Recipe not found"))
            } else {
                Result.Success(recipe)
            }
        }
    }

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            delay(400)
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                Result.Success(recipesWithResources)
            }
        }
    }

    override fun observeFavorites(): Flow<Set<String>> = favorites

    override suspend fun toggleFavorite(recipeId: String) {
        mutex.withLock {
            val set = favorites.value.toMutableSet()
            set.addOrRemove(recipeId)
            favorites.value = set.toSet()
        }
    }

    private var requestCount = 0

    private fun shouldRandomlyFail(): Boolean = ++requestCount % 20 == 0
}