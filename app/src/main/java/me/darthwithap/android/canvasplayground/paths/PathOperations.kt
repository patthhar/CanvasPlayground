package me.darthwithap.android.canvasplayground.paths

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathOperations(
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier.fillMaxSize()) {
    val squareWithoutOp = Path().apply {
      addRect(Rect(Offset(200f, 200f), Size(200f, 200f)))
    }
    val squareWithoutOpFromTop = Path().apply {
      addRect(Rect(Offset.Zero, Size(200f, 200f)))
    }
    val circle = Path().apply {
      addOval(Rect(Offset(200f, 200f), 100f))
    }
    val pathWithOp = Path().apply {
      op(circle, squareWithoutOp, PathOperation.Union)
    }
    drawPath(
      path = squareWithoutOpFromTop,
      color = Color.Red,
      style = Stroke(width = 2.dp.toPx())
    )
    drawPath(
      path = squareWithoutOp,
      color = Color.Red,
      style = Stroke(width = 2.dp.toPx())
    )
    drawPath(
      path = circle,
      color = Color.Blue,
      style = Stroke(width = 2.dp.toPx())
    )
    drawPath(
      path = pathWithOp,
      color = Color.Green
    )
  }

}