package me.darthwithap.android.canvasplayground.weight_scale

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity

@Composable
fun ScaleScreen(
  modifier: Modifier = Modifier
) {
  var weight by remember {
    mutableStateOf(75)
  }
  val density = LocalDensity.current

  BoxWithConstraints(
    modifier = modifier.fillMaxSize()
  ) {
    val halfHeightInDp = with(density) { (constraints.maxHeight / 2f).toDp() }
    Scale(
      modifier = Modifier
        .fillMaxWidth()
        .height(halfHeightInDp)
        .align(Alignment.BottomCenter)
    ) {
      weight = it
    }
  }
}