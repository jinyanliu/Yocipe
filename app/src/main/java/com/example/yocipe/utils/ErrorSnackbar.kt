package com.example.yocipe.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yocipe.R
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.snackbarAction
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ErrorSnackbar(
    showError: Boolean,
    modifier: Modifier = Modifier,
    onErrorAction: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    launchInComposition(showError) {
        delay(timeMillis = 5000L)
        if (showError) {
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Snackbar(
            modifier = Modifier.padding(dimen16),
            text = { Text(stringResource(id = R.string.cannot_update_recipes)) },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = stringResource(id = R.string.retry).yocipeToUppercase(),
                        color = MaterialTheme.colors.snackbarAction
                    )
                }
            }
        )
    }
}