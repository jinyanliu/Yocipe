package com.example.yocipe.data

import android.content.Context
import com.example.yocipe.data.recipes.RecipesRepository
import com.example.yocipe.data.recipes.impl.FakeRecipesRepository

interface AppContainer {
    val recipesRepository: RecipesRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val recipesRepository: RecipesRepository by lazy {
        FakeRecipesRepository(resources = applicationContext.resources)
    }
}