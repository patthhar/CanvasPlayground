package me.darthwithap.android.canvasplayground.gender_picker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.darthwithap.android.canvasplayground.R

@Composable
fun GenderPicker(
  modifier: Modifier = Modifier,
  maleGradientColors: List<Color> = listOf(Color(0xFF6D6DFF), Color.Blue),
  femaleGradientColors: List<Color> = listOf(Color(0xFFEA76FF), Color.Magenta),
  distanceBetweenGenders: Dp = 40.dp,
  scaleFactor: Float = 20f,
  onGenderSelected: (Gender) -> Unit
) {
  val density = LocalDensity.current
  val tenDp = with(density) { 10.dp.toPx() }
  var selectedGender by remember {
    mutableStateOf<Gender>(Gender.Male)
  }
  var canvasCenter by remember {
    mutableStateOf(Offset.Unspecified)
  }

  val malePathString = stringResource(id = R.string.male_path)
  val femalePathString = stringResource(id = R.string.female_path)
  val malePath = remember {
    PathParser().parsePathString(malePathString).toPath()
  }
  val malePathBounds = remember {
    malePath.getBounds()
  }
  val femalePath = remember {
    PathParser().parsePathString(femalePathString).toPath()
  }
  val femalePathBounds = remember {
    femalePath.getBounds()
  }

  var malePathOffset by remember {
    mutableStateOf(Offset.Zero)
  }
  var femalePathOffset by remember {
    mutableStateOf(Offset.Zero)
  }
  var currentClickOffset by remember {
    mutableStateOf(Offset.Zero)
  }
  val maleSelectedGradientRadius = animateFloatAsState(
    targetValue = if (selectedGender is Gender.Male) (malePathBounds.height + tenDp) else 0f,
    animationSpec = tween(durationMillis = 500), label = "male selected"
  )
  val femaleSelectedGradientRadius = animateFloatAsState(
    targetValue = if (selectedGender is Gender.Female) (femalePathBounds.height + tenDp) else 0f,
    animationSpec = tween(durationMillis = 500), label = "female selected"
  )


  Canvas(modifier = modifier
    .fillMaxSize()
    .pointerInput(key1 = true) {
      detectTapGestures { offset ->
        val transformedMaleBoundsRect = Rect(
          offset = malePathOffset,
          size = malePathBounds.size * scaleFactor
        )
        val transformedFemaleBoundsRect = Rect(
          offset = femalePathOffset,
          size = femalePathBounds.size * scaleFactor
        )

        if (selectedGender !is Gender.Male && transformedMaleBoundsRect.contains(offset)) {
          currentClickOffset = offset
          selectedGender = Gender.Male
          onGenderSelected(Gender.Male)
        } else if (selectedGender !is Gender.Female && transformedFemaleBoundsRect.contains(offset)) {
          currentClickOffset = offset
          selectedGender = Gender.Female
          onGenderSelected(Gender.Female)
        }
      }
    }
  ) {
    canvasCenter = this.center
    malePathOffset = Offset(
      x = canvasCenter.x - (malePathBounds.width * scaleFactor) - distanceBetweenGenders.toPx() / 2f,
      y = canvasCenter.y - (malePathBounds.height * scaleFactor) / 2f
    )
    femalePathOffset = Offset(
      x = canvasCenter.x + (femalePathBounds.width * scaleFactor) / 2f + distanceBetweenGenders.toPx() / 2f,
      y = canvasCenter.y - (femalePathBounds.height * scaleFactor) / 2f
    )

    val maleClickOffset = if (currentClickOffset == Offset.Zero) {
      malePathBounds.center
    } else {
      (currentClickOffset - malePathOffset) / scaleFactor
    }
    val femaleClickOffset = if (currentClickOffset == Offset.Zero) {
      femalePathBounds.center
    } else {
      (currentClickOffset - femalePathOffset) / scaleFactor
    }

    translate(left = malePathOffset.x, top = malePathOffset.y) {
      scale(
        scale = scaleFactor,
        pivot = malePathBounds.topLeft
      ) {
        drawPath(
          path = malePath,
          color = Color.LightGray
        )
        clipPath(
          path = malePath
        ) {
          drawCircle(
            brush = Brush.radialGradient(
              colors = maleGradientColors,
              center = maleClickOffset,
              radius = maleSelectedGradientRadius.value + 1f
            ),
            radius = maleSelectedGradientRadius.value,
            center = maleClickOffset
          )
        }
      }
    }
    translate(left = femalePathOffset.x, top = femalePathOffset.y) {
      scale(
        scale = scaleFactor,
        pivot = femalePathBounds.topLeft
      ) {
        drawPath(
          path = femalePath,
          color = Color.LightGray
        )

        clipPath(
          path = femalePath
        ) {
          drawCircle(
            brush = Brush.radialGradient(
              colors = femaleGradientColors,
              center = femaleClickOffset,
              radius = femaleSelectedGradientRadius.value + 1f
            ),
            radius = femaleSelectedGradientRadius.value,
            center = femaleClickOffset
          )
        }
      }
    }
  }

}