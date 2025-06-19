package com.narsha.ui.component.modifier

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.`if`(enabled: Boolean, block: @Composable Modifier.() -> Modifier) = composed { if (enabled) block() else this }
