package me.darthwithap.android.canvasplayground.weight_scale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .height(halfHeightInDp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center

    ) {
      Text(
        text = "Choose your weight",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = Color.Black
        )
      )
      Spacer(modifier = Modifier.height(32.dp))
      Text(
        text = weight.toString(),
        style = MaterialTheme.typography.displayLarge.copy(
          color = Color.Black
        )
      )
    }

    Scale(
      modifier = Modifier
        .fillMaxWidth()
        .height(halfHeightInDp)
        .align(Alignment.BottomCenter)
    ) {
      weight = it
    }

    // Bottom Button
    Button(
      onClick = {}, modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 32.dp),
      colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
    ) {
      Text(text = "Next", color = Color.White)
    }
  }
}