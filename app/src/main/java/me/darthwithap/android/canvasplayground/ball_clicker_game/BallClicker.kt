package me.darthwithap.android.canvasplayground.ball_clicker_game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun BallClicker(
  radiusDp: Dp = 24.dp,
  enabled: Boolean = false,
  ballColor: Color = Color.Red,
  onBallClick: () -> Unit = {}
) {
  val radiusPx = with(LocalDensity.current) { radiusDp.toPx() }

  BoxWithConstraints(
    modifier = Modifier.fillMaxSize()
  ) {
    var ballPositionCenter by remember {
      mutableStateOf(
        randomOffset(
          radius = radiusPx,
          width = constraints.maxWidth,
          height = constraints.maxHeight
        )
      )
    }
    Canvas(modifier = Modifier
      .fillMaxSize()
      .pointerInput(key1 = enabled) {
        if (!enabled) {
          return@pointerInput
        }
        detectTapGestures {
          val distance = sqrt(
            (it.x - ballPositionCenter.x).pow(2) +
                (it.y - ballPositionCenter.y).pow(2)
          )
          if (distance <= radiusPx) {
            ballPositionCenter = randomOffset(radius = radiusPx, width = size.width, size.height)
            onBallClick()
          }
        }
      }) {
      drawCircle(
        color = ballColor,
        radius = radiusPx,
        center = ballPositionCenter
      )
    }
  }
}

private fun randomOffset(radius: Float, width: Int, height: Int): Offset {
  val x = Random.nextInt(radius.roundToInt(), (width - radius).roundToInt())
  val y = Random.nextInt(radius.roundToInt(), (height - radius).roundToInt())
  return Offset(x.toFloat(), y.toFloat())
}