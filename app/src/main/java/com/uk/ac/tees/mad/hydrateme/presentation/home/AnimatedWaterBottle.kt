package com.uk.ac.tees.mad.hydrateme.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uk.ac.tees.mad.hydrateme.ui.theme.HydrateMeTheme

@Composable
fun AnimatedWaterBottle(
    modifier: Modifier = Modifier,
    waterPercentage: Float,
    waterColor: Color = MaterialTheme.colorScheme.primary,
    bottleColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val animatedWaterPercentage by animateFloatAsState(targetValue = waterPercentage, label = "Water Animation")

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) { 
            val bottleWidth = size.width * 0.5f
            val bottleHeight = size.height * 0.8f
            val capHeight = size.height * 0.1f
            val cornerRadius = CornerRadius(20f, 20f)

            // Draw the bottle body
            drawRoundRect(
                color = bottleColor,
                topLeft = Offset(size.width * 0.25f, capHeight),
                size = Size(bottleWidth, bottleHeight),
                cornerRadius = cornerRadius
            )

            // Draw the water
            clipRect(top = capHeight + bottleHeight * (1 - animatedWaterPercentage)) {
                drawRoundRect(
                    color = waterColor,
                    topLeft = Offset(size.width * 0.25f, capHeight),
                    size = Size(bottleWidth, bottleHeight),
                    cornerRadius = cornerRadius
                )
            }

            // Draw the bottle cap
            drawRoundRect(
                color = bottleColor,
                topLeft = Offset(size.width * 0.35f, 0f),
                size = Size(size.width * 0.3f, capHeight),
                cornerRadius = CornerRadius(10f, 10f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAnimatedWaterBottle() {
    HydrateMeTheme {
        AnimatedWaterBottle(
            modifier = Modifier.size(200.dp),
            waterPercentage = 0.6f
        )
    }
}
