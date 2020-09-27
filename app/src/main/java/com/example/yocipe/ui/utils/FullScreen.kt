package com.example.yocipe.ui.utils

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16

@Composable
fun FullScreen(action: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(dimen16)
    ) {
        action()
    }
}