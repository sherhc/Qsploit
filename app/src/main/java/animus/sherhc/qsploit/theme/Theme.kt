package animus.sherhc.qsploit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

private val shapes = Shapes(
	small = RoundedCornerShape(percent = 50),
	medium = RoundedCornerShape(16f),
	large = CutCornerShape(
		topLeft = 16.dp,
		topRight = 0.dp,
		bottomRight = 0.dp,
		bottomLeft = 16.dp
	)
)

@Composable
fun AppTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colors = if (isSystemInDarkTheme()) DarkPalette else LightPalette,
		content = content,
		//typography = JetchatTypography,
		shapes = shapes
	)
}