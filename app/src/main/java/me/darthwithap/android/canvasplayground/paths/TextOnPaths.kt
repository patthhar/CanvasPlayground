package me.darthwithap.android.canvasplayground.paths

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp

@Composable
fun TextOnPaths(
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier.fillMaxSize()) {
    val path = Path().apply {
      moveTo(100f, 1000f)
      quadTo(600f, 400f, 1000f, 1000f)
    }
    drawContext.canvas.nativeCanvas.apply {
      drawTextOnPath(
        "Hello World",
        path,
        30f, 50f,
        Paint().apply {
          color = Color.RED
          textSize = 24.sp.toPx()
          textAlign = Paint.Align.CENTER
        }
      )
    }
  }
}