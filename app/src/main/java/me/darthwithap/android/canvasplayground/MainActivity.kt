package me.darthwithap.android.canvasplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.darthwithap.android.canvasplayground.ui.theme.CanvasPlaygroundTheme
import me.darthwithap.android.canvasplayground.weight_scale.Scale
import me.darthwithap.android.canvasplayground.weight_scale.ScaleScreen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CanvasPlaygroundTheme {
        ScaleScreen()
      }
    }
  }
}
