package me.darthwithap.android.canvasplayground.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun MyCanvas() {
  Canvas(
    modifier = Modifier
      .padding(20.dp)
      .size(width = 400.dp, height = 800.dp)
  ) {
    drawRect(
      color = Color.Red,
      size = size
    )
    drawRect(
      color = Color.Black,
      topLeft = Offset(20.dp.toPx(), 20.dp.toPx()),
      size = Size(300.dp.toPx(), 650.dp.toPx()),
      alpha = 0.5f,
      style = Stroke(
        width = 3.dp.toPx()
      )
    )
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color.Yellow, Color.Blue),
        center = center,
        radius = 50.dp.toPx()
      ),
      radius = 50.dp.toPx(),
      center = center
    )
    drawArc(
      color = Color.Green,
      startAngle = 0f,
      sweepAngle = 270f,
      useCenter = false,
      topLeft = Offset(200f, 500f),
      size = Size(300f, 300f),
      style = Stroke(
        width = 3.dp.toPx()
      )
    )
    drawArc(
      color = Color.LightGray,
      startAngle = 0f,
      sweepAngle = 120f,
      useCenter = true,
      topLeft = Offset(100f, 900f),
      size = Size(200f, 200f)
    )
    drawOval(
      color = Color.Magenta,
      topLeft = Offset(600f, 100f),
      size = Size(200f, 300f)
    )
    drawLine(
      color = Color.Cyan,
      start = Offset(300f, 1400f),
      end = Offset(700f, 1400f),
      strokeWidth = 5.dp.toPx()
    )
  }
}