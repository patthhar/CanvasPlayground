package me.darthwithap.android.canvasplayground.clock

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.Color as ComposeColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Clock(
  modifier: Modifier = Modifier,
  style: ClockStyleOptions = ClockStyleOptions()
) {
  val currentTime = remember {
    mutableStateOf(LocalDateTime.now())
  }
  val now = rememberUpdatedState(currentTime.value)
  val radius = style.radius
  var clockCenter by remember {
    mutableStateOf(Offset.Zero)
  }
  val TAG = "ClockCanvasTask"

  LaunchedEffect(Unit) {
    while (true) {
      delay(1000)  // Update every second
      currentTime.value = LocalDateTime.now()
    }
  }

  Canvas(modifier = modifier.fillMaxSize()) {
    clockCenter = center
    drawContext.canvas.nativeCanvas.apply {
      drawCircle(
        clockCenter.x, clockCenter.y, radius.toPx(), Paint().apply {
          color = Color.WHITE
          setShadowLayer(style.shadowRadius.toPx(), 0f, 0f, Color.argb(50, 0, 0, 0))
        }
      )
    }

    for (i in 0..360) {
      val markLineType: MarkLineType = when {
        i % 30 == 0 -> MarkLineType.HourMarkMarkLine
        i % 6 == 0 && i % 30 != 0 -> MarkLineType.NormalMarkLine
        else -> MarkLineType.NoMarkLine
      }
      val markLineColor: ComposeColor = when (markLineType) {
        is MarkLineType.NormalMarkLine -> style.normalMarkLineColor
        is MarkLineType.HourMarkMarkLine -> style.hourMarkLineColor
        else -> ComposeColor.White
      }
      val markLineLength: Float = when (markLineType) {
        is MarkLineType.NormalMarkLine -> style.normalMarkLineLength.toPx()
        is MarkLineType.HourMarkMarkLine -> style.hourMarkLineLength.toPx()
        else -> 0f
      }

      val angleInRadians: Float = i * (PI / 180f).toFloat()

      val lineStartPoint = Offset(
        x = cos(angleInRadians) * radius.toPx() + clockCenter.x,
        y = sin(angleInRadians) * radius.toPx() + clockCenter.y
      )
      val endPointRadius = radius.toPx() - markLineLength
      val lineEndPoint = Offset(
        x = cos(angleInRadians) * endPointRadius + clockCenter.x,
        y = sin(angleInRadians) * endPointRadius + clockCenter.y
      )

      drawLine(
        color = markLineColor,
        start = lineStartPoint,
        end = lineEndPoint,
        strokeWidth = 1.dp.toPx()
      )
    }

    // HOUR HAND
    val hourHandHourAngleDegrees = ((now.value.hour % 12) * (360 / 12).toFloat() - 90)
    val hourHandMinuteAngleDegrees = ((now.value.minute) * 0.5)
    val hourAngleInRadians = (hourHandHourAngleDegrees * (PI / 180f).toFloat() +
        hourHandMinuteAngleDegrees * (PI / 180f).toFloat()).toFloat()

    val hourHandEnd = Offset(
      x = cos(hourAngleInRadians) * style.hourHandLength.toPx() + clockCenter.x,
      y = sin(hourAngleInRadians) * style.hourHandLength.toPx() + clockCenter.y
    )
    drawLine(
      color = style.hourHandColor,
      start = clockCenter,
      end = hourHandEnd,
      strokeWidth = style.hourHandWidth.toPx()
    )
    // MINUTE HAND
    val minuteHandMinuteAngleDegrees = now.value.minute * (360 / 60).toFloat() - 90
    val minuteHandSecondAngleDegrees = ((now.value.second) * 0.1)
    val minuteAngleInRadians = (minuteHandMinuteAngleDegrees * (PI / 180f).toFloat() +
        minuteHandSecondAngleDegrees * (PI / 180f).toFloat()).toFloat()

    val minuteHandEnd = Offset(
      x = cos(minuteAngleInRadians) * style.minuteHandLength.toPx() + clockCenter.x,
      y = sin(minuteAngleInRadians) * style.minuteHandLength.toPx() + clockCenter.y
    )
    drawLine(
      color = style.minuteHandColor,
      start = clockCenter,
      end = minuteHandEnd,
      strokeWidth = style.minuteHandWidth.toPx()
    )
    // SECOND HAND
    val secondHandSecondAngleDegrees = ((now.value.second) * (360 / 60)) - 90
    val secondAngleInRadians = (secondHandSecondAngleDegrees * (PI / 180f)).toFloat()

    val secondHandEnd = Offset(
      x = cos(secondAngleInRadians) * style.secondHandLength.toPx() + clockCenter.x,
      y = sin(secondAngleInRadians) * style.secondHandLength.toPx() + clockCenter.y
    )
    drawLine(
      color = style.secondHandColor,
      start = clockCenter,
      end = secondHandEnd,
      strokeWidth = style.secondHandWidth.toPx()
    )
  }

}