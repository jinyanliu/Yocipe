package com.example.yocipe.ui.utils

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen8

@Composable
fun FullScreen(action: @Composable () -> Unit) = Box(
    modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .padding(dimen16)
) {
    action()
}


@Composable
fun FullScreenLoading() = FullScreen { CircularProgressIndicator() }

@Composable
fun FullScreenTextButton(textButtonMessage: String, textButtonAction: () -> Unit) = FullScreen {
    TextButton(onClick = textButtonAction) {
        Text(text = textButtonMessage)
    }
}

@Composable
fun FullScreenMessage(
    mainMessage: String? = null,
    instructionMessage: String? = null,
    textButtonMessage: String? = null,
    textButtonAction: () -> Unit
) = FullScreen {
    Column {
        val textStyle = MaterialTheme.typography.body2
        mainMessage?.let {
            Text(
                text = it,
                style = textStyle
            )
        }
        instructionMessage?.let {
            Text(
                text = it,
                style = textStyle,
                modifier = Modifier.padding(top = dimen8)
            )
        }
        textButtonMessage?.let {
            TextButton(
                onClick = { textButtonAction() },
                modifier = Modifier.padding(top = dimen8)
            ) {
                Text(
                    text = it,
                    style = textStyle
                )
            }
        }
    }
}