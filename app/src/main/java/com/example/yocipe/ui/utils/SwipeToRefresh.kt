package com.example.yocipe.ui.utils

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.offsetPx
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.platform.DensityAmbient
import com.example.yocipe.ui.theme.dimen80

private val RefreshDistance = dimen80

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToRefreshLayout(
    refreshingState: Boolean,
    onRefresh: () -> Unit,
    refreshIndicator: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = with(DensityAmbient.current) { RefreshDistance.toPx() }

    val state = rememberSwipeableState(refreshingState)
    onCommit(refreshingState) {
        state.animateTo(refreshingState)
    }
    onCommit(state.value) {
        if (state.value) {
            onRefresh()
        }
    }

    Stack(
        modifier = Modifier.swipeable(
            state = state,
            enabled = !state.value,
            anchors = mapOf(
                -refreshDistance to false,
                refreshDistance to true
            ),
            thresholds = { _, _ -> FractionalThreshold(0.5f) },
            orientation = Orientation.Vertical
        )
    ) {
        content()
        Box(Modifier.gravity(Alignment.TopCenter).offsetPx(y = state.offset)) {
            if (state.offset.value != -refreshDistance) {
                refreshIndicator()
            }
        }
    }
}