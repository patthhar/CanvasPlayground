package me.darthwithap.android.canvasplayground

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import me.darthwithap.android.canvasplayground.clock.Clock
import me.darthwithap.android.canvasplayground.paths.PathBasics
import me.darthwithap.android.canvasplayground.paths.PathLineAnimation
import me.darthwithap.android.canvasplayground.paths.PathOperations
import me.darthwithap.android.canvasplayground.ui.theme.CanvasPlaygroundTheme

class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CanvasPlaygroundTheme {
        PathLineAnimation()
      }
    }
  }
}
