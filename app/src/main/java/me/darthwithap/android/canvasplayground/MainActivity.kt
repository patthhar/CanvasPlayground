package me.darthwithap.android.canvasplayground

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import me.darthwithap.android.canvasplayground.gender_picker.GenderPicker
import me.darthwithap.android.canvasplayground.ui.theme.CanvasPlaygroundTheme

class MainActivity : ComponentActivity() {
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CanvasPlaygroundTheme {
        GenderPicker(
          modifier = Modifier.fillMaxSize()
        ) {

        }
      }
    }
  }
}
