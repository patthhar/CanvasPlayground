package me.darthwithap.android.canvasplayground.weight_scale

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScaleStyleOptions(
  val scaleWidth: Dp = 120.dp,
  val scaleRadius: Dp = 500.dp,
  val normalLineColor: Color = Color.LightGray,
  val fiveStepLineColor: Color = Color.Green,
  val tenStepLineColor: Color = Color.Black,
  val normalLineLength: Dp = 12.dp,
  val fiveStepLineLength: Dp = 24.dp,
  val tenStepLineLength: Dp = 32.dp,
  val scaleIndicatorColor: Color = Color.Green,
  val scaleIndicatorLength: Dp = 48.dp,
  val textSize: TextUnit = 18.sp
)
