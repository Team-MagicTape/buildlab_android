package com.narsha.ui.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val main: Color,
    val liteMain: Color,
    val bg: Color,
    val white: Color,
    val black: Color,
    val grayBorder: Color,
    val textGray: Color,
    val placeHolder: Color,
    val error: Color,
    val border: Color,
    val logoColor: Color
)

val LightAppColors = AppColorScheme(
    main = Color(0xFF49D47C),
    liteMain = Color(0xFF62DA8E),
    bg = Color(0xFFF7F7F7),
    white = Color.White,
    black = Color(0xFF121212),
    grayBorder = Color(0xFFDCDDDE),
    textGray = Color(0xFF747678),
    placeHolder = Color(0xFF9E9E9E),
    error = Color(0xFFFF3120),
    border = Color(0xFFDCDDDE),
    logoColor = Color(0xFF121212)
)

val DarkAppColors = LightAppColors.copy(
    bg = Color(0xFF2B2B2B),
    white = Color(0xFF121212),
    black = Color.White,
    grayBorder = Color(0xFF747678),
    logoColor = Color(0xFFDADDE1)
)

internal val LocalColors = staticCompositionLocalOf { LightAppColors }
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors
    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}


object ColorTheme {
    val colors: AppColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}
