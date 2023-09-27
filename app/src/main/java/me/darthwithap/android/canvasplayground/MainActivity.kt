package me.darthwithap.android.canvasplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.darthwithap.android.canvasplayground.ball_clicker_game.MainScreen
import me.darthwithap.android.canvasplayground.ui.theme.CanvasPlaygroundTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CanvasPlaygroundTheme {
        MainScreen()
      }
    }
  }
}
