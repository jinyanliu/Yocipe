package com.example.yocipe.ui

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yocipe.utils.getMutableStateOf

enum class ScreenName { HOME, FAVORITES, RECIPE }

sealed class Screen(val id: ScreenName) {
    object Home : Screen(ScreenName.HOME)
    object Favorites : Screen(ScreenName.FAVORITES)
    data class Recipe(val recipeId: String) : Screen(ScreenName.RECIPE)
}

private const val SIS_SCREEN = "sis_screen"
private const val SIS_NAME = "screen_name"
private const val SIS_RECIPE = "recipe"

private fun Screen.toBundle(): Bundle {
    return bundleOf(SIS_NAME to id.name).also {
        if (this is Screen.Recipe) {
            it.putString(SIS_RECIPE, recipeId)
        }
    }
}

private fun Bundle.toScreen(): Screen {
    return when (ScreenName.valueOf(getStringOrThrow(SIS_NAME))) {
        ScreenName.HOME -> Screen.Home
        ScreenName.FAVORITES -> Screen.Favorites
        ScreenName.RECIPE -> {
            val recipeId = getStringOrThrow(SIS_RECIPE)
            Screen.Recipe(recipeId)
        }
    }
}

private fun Bundle.getStringOrThrow(key: String) =
    requireNotNull(getString(key)) { "Missing key '$key' in $this" }

class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    var lastScreen: Screen? = null

    var currentScreen: Screen by savedStateHandle.getMutableStateOf(
        key = SIS_SCREEN,
        default = Screen.Home,
        save = { it.toBundle() },
        restore = { it.toScreen() }
    )
        private set

    @MainThread
    fun onBack(): Boolean {
        val wasHandled = currentScreen != Screen.Home
        currentScreen = lastScreen ?: Screen.Home
        return wasHandled
    }

    @MainThread
    fun navigateTo(screen: Screen) {
        lastScreen = currentScreen
        currentScreen = screen
    }
}