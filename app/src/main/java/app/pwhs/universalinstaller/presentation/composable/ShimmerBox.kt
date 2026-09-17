package app.pwhs.universalinstaller.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import app.pwhs.core.ui.component.ShimmerBox as CoreShimmerBox

@Composable
fun ShimmerBox(modifier: Modifier = Modifier, shape: Shape) {
    CoreShimmerBox(modifier = modifier, shape = shape)
}
