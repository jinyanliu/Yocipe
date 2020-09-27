package com.example.yocipe.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen8

@Composable
fun Spacer16() = Spacer(Modifier.preferredHeight(dimen16))

@Composable
fun Spacer8() = Spacer(Modifier.preferredHeight(dimen8))