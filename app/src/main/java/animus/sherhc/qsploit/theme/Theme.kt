package animus.sherhc.qsploit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkPalette = darkColors(
	primary = Color(0xFF1EB980),
	onPrimary = Color.Black,
	secondary = Color(0xFFb91e57),
	onSecondary = Color.White,
	surface = Color(0xFF121212),
	onSurface = Color.White,
)

private val LightPalette = lightColors(
	primary = Color(0xFF3377FF),
	onPrimary = Color.White,
	secondary = Color(0xFFffbb33),
	onSecondary = Color.Black,
	surface = Color.White,
	onSurface = Color.Black,
)

@Composable
fun AppTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (isSystemInDarkTheme()) DarkPalette else LightPalette,
		content = content,
		//typography = JetchatTypography,
		//shapes = JetchatShapes
	)
}