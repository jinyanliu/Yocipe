package com.example.yocipe.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yocipe.ui.theme.dimen16
import com.example.yocipe.ui.theme.dimen8

@Composable
fun VerticalSpacer16() = Spacer(Modifier.preferredHeight(dimen16))

@Composable
fun VerticalSpacer8() = Spacer(Modifier.preferredHeight(dimen8))

@Composable
fun HorizontalSpacer16() = Spacer(Modifier.preferredWidth(dimen16))

@Composable
fun HorizontalSpacer8() = Spacer(Modifier.preferredWidth(dimen8))