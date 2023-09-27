package me.darthwithap.android.canvasplayground.ball_clicker_game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
  var points by remember {
    mutableStateOf(0)
  }
  var isTimerRunning by remember {
    mutableStateOf(false)
  }

  Column(modifier = Modifier.fillMaxSize()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = "Points: $points", fontSize = 20.sp, fontWeight = FontWeight.Bold)
      Button(onClick = {
        isTimerRunning = !isTimerRunning
        points = 0
      }) {
        Text(text = if (isTimerRunning) "Reset" else "Start")
      }
      CountdownTimer(
        isTimerRunning = isTimerRunning
      ) {
        isTimerRunning = false
      }
    }
    BallClicker(enabled = isTimerRunning, onBallClick = { points++ })
  }
}

@Composable
fun CountdownTimer(
  time: Int = 30000,
  isTimerRunning: Boolean = false,
  onTimerEnd: () -> Unit = {}
) {
  var currTime by remember {
    mutableStateOf(time)
  }
  LaunchedEffect(key1 = currTime, key2 = isTimerRunning) {
    if (!isTimerRunning) {
      currTime = time
      return@LaunchedEffect
    }
    if (currTime > 0) {
      delay(1000)
      currTime -= 1000
    } else {
      onTimerEnd.invoke()
    }
  }
  Text(
    text = (currTime / 1000).toString(),
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
  )
}