package com.example.yocipe.ui.utils

import androidx.compose.foundation.layout.RowScope.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen8

@Composable
fun Spacer16Vertical() = Spacer(Modifier.preferredHeight(dimen16))

@Composable
fun Spacer8Vertical() = Spacer(Modifier.preferredHeight(dimen8))

@Composable
fun Spacer16Horizontal() = Spacer(Modifier.preferredWidth(dimen16))

@Composable
fun Spacer8Horizontal() = Spacer(Modifier.preferredWidth(dimen8))

@Composable
fun SpacerFillMax()=  Spacer(modifier = Modifier.weight(1f))