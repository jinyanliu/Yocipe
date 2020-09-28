package com.example.yocipe.ui.utils

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = { onClick() }
    ) {
        modifier.fillMaxSize()
        if (isFavorite) {
            Icon(
                asset = Icons.Filled.Favorite,
                modifier = modifier
            )
        } else {
            Icon(
                asset = Icons.Filled.FavoriteBorder,
                modifier = modifier
            )
        }
    }
}