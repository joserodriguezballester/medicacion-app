package com.example.prueba3.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundWrapper(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BCD4), // Cyan
                        Color(0xFF3F51B5), // Indigo
                        Color(0xFF9C27B0)  // Purple
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun BackgroundWrapperInventarioCard(
    modifier: Modifier = Modifier,
    colorFondo: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Cyan.copy(alpha = 0.2f),
                spotColor = Color.Blue.copy(alpha = 0.5f)
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colorFondo.copy(alpha = 0.9f),
                        Color.Transparent
                    ),
                    center = Offset(100f, 300f),
                    radius = 900f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00BCD4), // Cyan
                        Color(0xFF3F51B5), // Indigo
                        Color(0xFF9C27B0)  // Purple
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StyledTratamientoWrapper(
    modifier: Modifier = Modifier,
    colorFondo: Color,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Cyan.copy(alpha = 0.2f),
                spotColor = Color.Blue.copy(alpha = 0.5f)
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colorFondo.copy(alpha = 0.9f),
                        Color.Transparent
                    ),
                    center = Offset(100f, 300f),
                    radius = 900f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Cyan, Color.Blue, Color.Magenta)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        content()
    }
}
