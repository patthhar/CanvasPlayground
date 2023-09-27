package me.darthwithap.android.canvasplayground

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CanvasText() {
  val density = LocalDensity.current
  Canvas(modifier = Modifier.fillMaxSize()) {
    drawContext.canvas.nativeCanvas.apply {
      drawText("This is text from canvas text", 100f, 100f, Paint().apply {
        color = Color.BLUE
        textSize = with(density) { 24.dp.toPx() }
      })
    }
  }
}