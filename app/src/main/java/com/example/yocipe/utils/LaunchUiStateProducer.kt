package com.example.yocipe.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.launchInComposition
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.yocipe.data.Result
import com.example.yocipe.ui.state.UiState
import com.example.yocipe.ui.state.copyWithResult
import kotlinx.coroutines.channels.Channel

data class ProducerResult<T>(
    val result: State<T>,
    val onRefresh: () -> Unit,
    val onClearError: () -> Unit
)

@Composable
fun <Producer, T> launchUiStateProducer(
    producer: Producer,
    block: suspend Producer.() -> Result<T>
): ProducerResult<UiState<T>> = launchUiStateProducer(producer, Unit, block)

@Composable
fun <Producer, T> launchUiStateProducer(
    producer: Producer,
    key: Any?,
    block: suspend Producer.() -> Result<T>
): ProducerResult<UiState<T>> {
    val producerState = remember {
        mutableStateOf(UiState<T>(loading = true))
    }

    val refreshChannel = remember { Channel<Unit>(Channel.CONFLATED) }

    val refresh: () -> Unit = { refreshChannel.offer(Unit) }

    val clearError: () -> Unit = {
        producerState.value = producerState.value.copy(exception = null)
    }

    launchInComposition(producer, key) {
        producerState.value = UiState(loading = true)
        refreshChannel.send(Unit)

        for (refreshEvent in refreshChannel) {
            producerState.value = producerState.value.copy(loading = true)
            producerState.value = producerState.value.copyWithResult(producer.block())
        }
    }
    return ProducerResult(producerState, refresh, clearError)
}