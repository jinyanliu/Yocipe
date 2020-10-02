package com.example.yocipe.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.core.os.ConfigurationCompat

@Composable
fun String.yocipeToUppercase() = this.toUpperCase(
    ConfigurationCompat.getLocales(ConfigurationAmbient.current).get(0)
)