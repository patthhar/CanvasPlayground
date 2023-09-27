package me.darthwithap.android.canvasplayground.weight_scale

import android.graphics.Color
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Scale(
  modifier: Modifier,
  style: ScaleStyleOptions = ScaleStyleOptions(),
  minWeight: Int = 20,
  maxWeight: Int = 200,
  initialWeight: Int = 75,
  onWeightChange: (Int) -> Unit
) {
  val radius = style.scaleRadius
  val scaleWidth = style.scaleWidth
  var center by remember {
    mutableStateOf(Offset.Zero)
  }
  var circleCenter by remember {
    mutableStateOf(Offset.Zero)
  }
  var angle by remember {
    mutableStateOf(0f)
  }
  var dragStartAngle by remember {
    mutableStateOf(0f)
  }
  var oldAngle by remember {
    mutableStateOf(angle)
  }
  var currentWeight by remember {
    mutableStateOf(initialWeight)
  }
  val density = LocalDensity.current
  val currentView = LocalView.current

  Canvas(
    modifier = modifier.pointerInput(key1 = true) {
      detectDragGestures(
        onDragStart = { startOffset ->
          // Start angle of point of touch in degrees
          dragStartAngle = -atan2(
            x = circleCenter.y - startOffset.y,
            y = circleCenter.x - startOffset.x
          ) * (180f / PI.toFloat())
        },
        onDragEnd = {
          oldAngle = angle
        }
      ) { change, _ ->
        val touchAngle = -atan2(
          x = circleCenter.y - change.position.y,
          y = circleCenter.x - change.position.x
        ) * (180f / PI.toFloat())
        val newAngle = (touchAngle - dragStartAngle) + oldAngle
        angle = newAngle.coerceIn(
          minimumValue = initialWeight - maxWeight.toFloat(),
          maximumValue = initialWeight - minWeight.toFloat()
        )
        val newWeight = (initialWeight - angle).roundToInt()
        if (newWeight != currentWeight) {
          currentView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
          currentWeight = newWeight
          onWeightChange(newWeight)
        }
      }
    }
  ) {
    center = this.center
    circleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx())
    val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
    val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f
    val shadowRadius = with(density) { 4.dp.toPx() }

    drawContext.canvas.nativeCanvas.apply {
      drawCircle(
        circleCenter.x, circleCenter.y,
        radius.toPx(),
        Paint().apply {
          strokeWidth = scaleWidth.toPx()
          color = WHITE
          setStyle(Paint.Style.STROKE)
          setShadowLayer(
            shadowRadius, 0f,
            0f,
            Color.argb(50, 0, 0, 0)
          )
        }
      )
    }

    // Draw Lines for degrees
    for (i in minWeight..maxWeight) {
      val angleInRadians = (i - initialWeight + angle - 90) * (PI / 180f).toFloat()
      val lineType: LineType = when {
        i % 10 == 0 -> LineType.TenStep
        i % 5 == 0 && i % 10 != 0 -> LineType.FiveStep
        else -> LineType.Normal
      }
      val lineColor = when (lineType) {
        is LineType.Normal -> style.normalLineColor
        is LineType.FiveStep -> style.fiveStepLineColor
        is LineType.TenStep -> style.tenStepLineColor
      }
      val lineLength = when (lineType) {
        is LineType.Normal -> style.normalLineLength.toPx()
        is LineType.FiveStep -> style.fiveStepLineLength.toPx()
        is LineType.TenStep -> style.tenStepLineLength.toPx()
      }

      val linePointOnOuterCircleEdge: Offset = Offset(
        x = cos(angleInRadians) * outerRadius + circleCenter.x,
        y = sin(angleInRadians) * outerRadius + circleCenter.y
      )
      val pointInsideScaleRadius = outerRadius - lineLength
      val linePointInsideTheScale = Offset(
        x = cos(angleInRadians) * pointInsideScaleRadius + circleCenter.x,
        y = sin(angleInRadians) * pointInsideScaleRadius + circleCenter.y
      )

      // Drawing the Text - (70, 80..) and so using nativeCanvas
      drawContext.canvas.nativeCanvas.apply {
        if (lineType == LineType.TenStep) {
          val textPositionRadius = outerRadius - lineLength - style.textSize.toPx() - 8.dp.toPx()
          val x = cos(angleInRadians) * textPositionRadius + circleCenter.x
          val y = sin(angleInRadians) * textPositionRadius + circleCenter.y

          // Rotates the text under the TenStep Lines
          withRotation(
            degrees = angleInRadians * (180f) / PI.toFloat() + 90f,
            pivotX = x,
            pivotY = y
          ) {
            // Draws the text under TenStep Lines
            drawText(i.toString(), x, y, Paint().apply {
              textSize = style.textSize.toPx()
              textAlign = Paint.Align.CENTER
            })
          }
        }
      }

      drawLine(
        color = lineColor,
        start = linePointInsideTheScale,
        end = linePointOnOuterCircleEdge,
        strokeWidth = 1.dp.toPx()
      )

      // Scale Indicator
      val middleTopPoint = Offset(
        x = circleCenter.x,
        y = circleCenter.y - innerRadius - style.scaleIndicatorLength.toPx()
      )
      val bottomLeftPoint = Offset(
        x = circleCenter.x - 2.dp.toPx(),
        y = circleCenter.y - innerRadius
      )
      val bottomRightPoint = Offset(
        x = circleCenter.x + 2.dp.toPx(),
        y = circleCenter.y - innerRadius
      )

      val indicatorPath = Path().apply {
        moveTo(middleTopPoint.x, middleTopPoint.y)
        lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        lineTo(bottomRightPoint.x, bottomRightPoint.y)
        lineTo(middleTopPoint.x, middleTopPoint.y)
      }

      drawPath(
        path = indicatorPath,
        color = style.scaleIndicatorColor
      )
    }
  }
}