package me.darthwithap.android.canvasplayground.paths

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun TransformationAndClippings(
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier.fillMaxSize()) {
    rotate(30f, pivot = Offset(200f, 200f)) {
      drawRect(
        color = Color.Red,
        topLeft = Offset(100f, 100f),
        size = Size(200f, 200f)
      )
    }
    translate(left = 300f, top = 300f) {
      rotate(30f, pivot = Offset(100f, 100f)) {
        drawRect(
          color = Color.Blue,
          topLeft = Offset(200f, 200f),
          size = Size(200f, 200f)
        )
      }
    }
    scale(0.5f, Offset(200f, 200f)) {
      drawRect(
        color = Color.Green,
        topLeft = Offset(100f, 100f),
        size = Size(200f, 200f)
      )
    }
  }
}