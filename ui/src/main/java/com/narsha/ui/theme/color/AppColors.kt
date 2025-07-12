package com.narsha.ui.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group

data class AppColorScheme(
    val primary: Primary,
    val black: Color,
    val white: Color,
    val gray: Gray,
    val error: Color,
    val background: Color,
    val shadow: Shadow
)

data class Primary(
    val normal: Color,
    val lite: Color,
    val active: Color,
    val hover: Color,
)

data class Gray(
    val normal: Color,
    val dark: Color,
    val lite: Color,
    val superSuperLight: Color = Color(0xFFF1F1F1),
    val superDark: Color = Color(0xFF515151),
)

data class Shadow(
    val primary: Color,
    val dark: Color,
    val lite: Color,
)

val LightAppColors = AppColorScheme(
    primary = Primary(
        normal = Color(0xFFFF7375),
        lite = Color(0xFFFFF1F1),
        active = Color(0xFFCC5C5E),
        hover = Color(0xFFFFEAEA),
    ),
    black = Color(0xFF121212),
    white = Color(0xFFFFFFFF),
    gray = Gray(
        normal = Color(0xFFD1D1D1),
        dark = Color(0xFFA9A9A9),
        lite = Color(0xFFE8E8E8),
    ),
    error = Color(0xFFFF3120),
    background = Color(0xFFFBF9F7),
    shadow = Shadow(
        primary = Color(0xFFFF6669),
        dark = Color(0xFFEDEDED),
        lite = Color(0xFFF8F8F8),
    )
)

val DarkAppColors = LightAppColors.copy(

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
