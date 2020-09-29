package com.example.yocipe.ui.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.SwipeToRefreshLayout
import com.example.yocipe.ui.theme.dimen10
import com.example.yocipe.ui.theme.dimen36
import com.example.yocipe.ui.theme.dimen4

@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeToRefreshLayout(
            refreshingState = loading,
            onRefresh = onRefresh,
            refreshIndicator = {
                Surface(elevation = dimen10, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier.preferredSize(dimen36).padding(dimen4)
                    )
                }
            },
            content = content
        )
    }
}