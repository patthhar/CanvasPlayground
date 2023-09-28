package me.darthwithap.android.canvasplayground.clock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyleOptions(
  val radius: Dp = 120.dp,
  val hourHandLength: Dp = 90.dp,
  val hourHandWidth: Dp = 6.dp,
  val hourHandColor: Color = Color.Black,
  val minuteHandLength: Dp = 100.dp,
  val minuteHandWidth: Dp = 4.dp,
  val minuteHandColor: Color = Color.Black,
  val secondHandLength: Dp = 105.dp,
  val secondHandWidth: Dp = 2.dp,
  val secondHandColor: Color = Color.Red,
  val hourMarkLineLength: Dp = 15.dp,
  val hourMarkLineColor: Color = Color.Black,
  val normalMarkLineLength: Dp = 10.dp,
  val normalMarkLineColor: Color = Color.LightGray,
  val shadowRadius: Dp = 5.dp
)