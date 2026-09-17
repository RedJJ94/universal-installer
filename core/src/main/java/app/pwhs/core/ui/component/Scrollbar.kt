package app.pwhs.core.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Renders a subtle vertical scrollbar overlay for any [ScrollState].
 */
fun Modifier.verticalScrollbar(
    scrollState: ScrollState,
    color: Color = Color.Unspecified,
    width: Dp = 4.dp,
    padding: Dp = 2.dp,
    autoHide: Boolean = false,
): Modifier = composed {
    val targetAlpha = if (autoHide) {
        if (scrollState.isScrollInProgress) 1f else 0.4f
    } else 0.7f

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 200),
        label = "scrollbar_alpha",
    )

    val thumbColor = if (color != Color.Unspecified) color else MaterialTheme.colorScheme.onSurfaceVariant

    drawWithContent {
        drawContent()

        val viewportHeight = size.height
        val totalHeight = scrollState.maxValue + viewportHeight
        if (totalHeight <= viewportHeight || viewportHeight <= 0f) return@drawWithContent

        val thickness = width.toPx()
        val pad = padding.toPx()
        val thumbHeight = (viewportHeight / totalHeight * viewportHeight).coerceIn(24.dp.toPx(), viewportHeight)
        val scrollProgress = scrollState.value.toFloat() / scrollState.maxValue.coerceAtLeast(1)
        val thumbOffsetY = scrollProgress * (viewportHeight - thumbHeight)

        if (alpha > 0f) {
            drawRoundRect(
                color = thumbColor.copy(alpha = thumbColor.alpha * alpha),
                topLeft = Offset(size.width - thickness - pad, thumbOffsetY),
                size = Size(thickness, thumbHeight),
                cornerRadius = CornerRadius(thickness / 2f, thickness / 2f),
            )
        }
    }
}
