package com.example.loukatah.presentation.view.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Shimmer loading effect for a single item
 * 
 * @param brush Brush for the shimmer effect
 * @param modifier Modifier for customizing the layout
 */
@Composable
fun ShimmerLoading(
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
        }
    }
}

/**
 * Creates an animated shimmer effect
 * 
 * @param modifier Modifier for customizing the layout
 */
@Composable
fun AnimatedShimmer(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
    
    ShimmerLoading(brush = brush, modifier = modifier)
}

/**
 * Creates multiple shimmer loading items
 * 
 * @param count Number of shimmer items to display
 * @param modifier Modifier for customizing the layout
 */
@Composable
fun ShimmerItemList(
    count: Int = 7,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        repeat(count) {
            AnimatedShimmer()
        }
    }
}
