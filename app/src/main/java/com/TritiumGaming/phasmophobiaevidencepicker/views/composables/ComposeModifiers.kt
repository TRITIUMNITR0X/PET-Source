package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.DisplayOrientation.HORIZONTAL
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.DisplayOrientation.VERTICAL

object DisplayOrientation {
    const val HORIZONTAL: Int = 0
    const val VERTICAL: Int = 1
}

fun Modifier.fadingEdges(
    scrollState: ScrollState,
    orientation: Int = HORIZONTAL,
    topEdgeHeight: Dp = 32.dp,
    bottomEdgeHeight: Dp = 32.dp,
    leftEdgeWidth: Dp = 32.dp,
    rightEdgeWidth: Dp = 32.dp
): Modifier = this.then(
    Modifier
        // adding layer fixes issue with blending gradient and content
        .graphicsLayer { alpha = 0.99F }
        .drawWithContent {
            drawContent()

            if (orientation == VERTICAL) {
                val topColors = listOf(Color.Transparent, Color.Black)
                val topStartY = scrollState.value.toFloat()
                val topGradientHeight = topEdgeHeight.toPx().coerceAtMost(topStartY)
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = topColors,
                        startY = topStartY,
                        endY = topStartY + topGradientHeight
                    ),
                    blendMode = BlendMode.DstIn
                )

                val bottomColors = listOf(Color.Black, Color.Transparent)
                val bottomEndY = size.height - scrollState.maxValue + scrollState.value
                val bottomGradientHeight = bottomEdgeHeight
                    .toPx()
                    .coerceAtMost(scrollState.maxValue.toFloat() - scrollState.value)
                if (bottomGradientHeight != 0f) drawRect(
                    brush = Brush.verticalGradient(
                        colors = bottomColors,
                        startY = bottomEndY - bottomGradientHeight,
                        endY = bottomEndY
                    ),
                    blendMode = BlendMode.DstIn
                )
            } else {
                val leftColors = listOf(Color.Transparent, Color.Black)
                val leftStartY = scrollState.value.toFloat()
                val leftGradientWidth = leftEdgeWidth.toPx()
                    .coerceAtMost(leftStartY)
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = leftColors,
                        startX = leftStartY,
                        endX = leftStartY + leftGradientWidth
                    ),
                    blendMode = BlendMode.DstIn
                )

                val rightColors = listOf(Color.Black, Color.Transparent)
                val rightEndY = size.width - scrollState.maxValue + scrollState.value
                val rightGradientWidth = rightEdgeWidth.toPx()
                    .coerceAtMost(scrollState.maxValue.toFloat() - scrollState.value)
                if (rightGradientWidth != 0f) drawRect(
                    brush = Brush.horizontalGradient(
                        colors = rightColors,
                        startX = rightEndY - rightGradientWidth,
                        endX = rightEndY
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
        }
)