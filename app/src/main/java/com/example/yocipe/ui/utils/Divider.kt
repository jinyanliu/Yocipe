package com.example.yocipe.ui.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16

@Composable
fun Divider(modifier: Modifier = Modifier.padding(horizontal = dimen16)) = Divider(
    modifier = modifier,
    color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
)