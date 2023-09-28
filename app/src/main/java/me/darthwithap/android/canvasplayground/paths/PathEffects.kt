package me.darthwithap.android.canvasplayground.paths

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathEffects(
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
  val phase: Float by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 10000f,
    animationSpec = infiniteRepeatable(
      animation = tween(60000, easing = LinearEasing)
    ), label = "phase"
  )

  val dashedPath = Path().apply {
    moveTo(100f, 100f)
    cubicTo(100f, 300f, 600f, 700f, 600f, 1000f)
  }
  val cornerPath = Path().apply {
    moveTo(100f, 300f)
    cubicTo(100f, 500f, 500f, 900f, 500f, 1200f)
    lineTo(900f, 900f)
    lineTo(1100f, 1200f)
  }
  Canvas(modifier = modifier.fillMaxSize()) {
    drawPath(
      path = dashedPath,
      color = Color.Blue,
      style = Stroke(
        width = 5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(
          intervals = floatArrayOf(16.dp.toPx(), 12.dp.toPx()),
          phase = phase
        )
      )
    )
    // To round at corner
    drawPath(
      path = cornerPath,
      color = Color.Red,
      style = Stroke(
        width = 5.dp.toPx(), pathEffect = PathEffect.cornerPathEffect(
          radius = 1000f
        )
      )
    )
    val ellipse = Path().apply {
      addOval(Rect(topLeft = Offset.Zero, bottomRight = Offset(40f, 10f)))
    }
    drawPath(
      path = cornerPath,
      color = Color.Green,
      style = Stroke(
        width = 5.dp.toPx(), pathEffect = PathEffect.chainPathEffect(
          PathEffect.cornerPathEffect(radius = 1000f), PathEffect.stampedPathEffect(
            shape = ellipse,
            advance = 100f,
            phase = phase,
            style = StampedPathEffectStyle.Morph
          )
        )
      )
    )
  }
}