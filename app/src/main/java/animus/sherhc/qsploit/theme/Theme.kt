package animus.sherhc.qsploit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Yellow400 = Color(0xFFF6E547)
private val Yellow700 = Color(0xFFF3B711)
private val Yellow800 = Color(0xFFF29F05)

private val Blue200 = Color(0xFF9DA3FA)
private val Blue400 = Color(0xFF4860F7)
private val Blue500 = Color(0xFF0540F2)
private val Blue800 = Color(0xFF001CCF)

private val Red300 = Color(0xFFEA6D7E)
private val Red800 = Color(0xFFD00036)

private val DarkPalette = darkColors(
	primary = Color(0x1EB980),
	onPrimary = Color.Black,
	secondary = Color(0xb91e57),
	onSecondary = Color.White,
	surface = Color(0x121212),
	onSurface = Color.White,
)

private val LightPalette = lightColors(
	primary = Color(0x3377FF),
	onPrimary = Color.White,
	secondary = Color(0xffbb33),
	onSecondary = Color.Black,
	surface = Color.White,
	onSurface = Color.Black,
)

@Composable
fun AppTheme(
	isDarkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {

	MaterialTheme(
		colors = if (isDarkTheme) DarkPalette else LightPalette,
		content = content,
		//typography = JetchatTypography,
		//shapes = JetchatShapes
	)
}