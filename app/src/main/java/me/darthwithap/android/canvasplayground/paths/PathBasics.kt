package me.darthwithap.android.canvasplayground.paths

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathBasics(
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier.fillMaxSize()) {
    val path = Path().apply {
      moveTo(700f, 100f)
      lineTo(100f, 500f)
      lineTo(500f, 500f)
      //quadraticBezierTo(1000f, 300f, 500f, 100f)
      cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
    }
    drawPath(
      path = path,
      color = Color.Red,
      style = Stroke(
        width = 6.dp.toPx(),
        cap = StrokeCap.Round
      )
    )
  }
}