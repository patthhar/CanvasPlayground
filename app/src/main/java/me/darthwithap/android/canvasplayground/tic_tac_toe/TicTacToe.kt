package me.darthwithap.android.canvasplayground.tic_tac_toe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun TicTacToe(
        modifier: Modifier = Modifier,
        gridSize: Int = 3,
        height: Float,
        canvasPadding: Dp = 16.dp,
        onPlayerWin: (Turn?) -> Unit,
        onNewRound: () -> Unit = {}
) {
    val density = LocalDensity.current
    val canvasHeight = with(density) { (height / 2f).toDp() }
    val scope = rememberCoroutineScope()
    var turn by remember {
        mutableStateOf<Turn>(Turn.O)
    }
    var animations = remember {
        emptyAnimations(gridSize)
    }
    var isGameRunning by remember {
        mutableStateOf(false)
    }
    var cellSize by remember {
        mutableStateOf(Size.Zero)
    }
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var gameBoard by remember {
        mutableStateOf(emptyGameBoard(gridSize))
    }
    var cellState by remember {
        mutableStateOf(emptyCellState(gridSize))
    }

    Canvas(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(canvasPadding)
                    .height(canvasHeight)
                    .pointerInput(key1 = Unit) {
                        detectTapGestures { offset ->
                            val row = (offset.y / cellSize.height).toInt() % gridSize
                            val col = (offset.x / cellSize.width).toInt() % gridSize

                            if (!isGameRunning) {
                                return@detectTapGestures
                            }

                            if (cellState[row][col] == CellState.EMPTY) {
                                val currTurn = if (turn == Turn.O) CellState.O else CellState.X

                                gameBoard[row][col] = if (turn == Turn.O) 1 else -1
                                cellState = updateCellState(cellState, row, col, currTurn)
                                cellState[row][col] = if (turn == Turn.O) CellState.O else CellState.X
                                val targetValue = if (turn == Turn.O) 1f else 2f
                                scope.animate(animations[row][col], targetValue)
                            }

                            // Checking game status
                            val isGridFull = cellState.all {
                                it.all { state -> state != CellState.EMPTY }
                            }
                            val result = checkForWin(gameBoard, gridSize)
                            if (result) {
                                onPlayerWin(turn)
                            }
                            if (isGridFull || result) {
                                scope.launch {
                                    isGameRunning = false
                                    if (isGridFull) onPlayerWin(Turn.None)
                                    delay(5000L)
                                    isGameRunning = true
                                    cellState = emptyCellState(gridSize)
                                    gameBoard = emptyGameBoard(gridSize)
                                    animations = emptyAnimations(gridSize)
                                    onNewRound()
                                }
                            }
                            // Switch turns
                            turn = !turn
                        }
                    }
    ) {
        center = this.center
        val gridHeight = size.height
        val gridWidth = size.width
        val cellHeight = gridHeight / gridSize
        val cellWidth = gridWidth / gridSize
        cellSize = Size(cellWidth, cellHeight)

        drawGrid(
                gridSize = gridSize,
                gridHeight = gridHeight,
                gridWidth = gridWidth,
                cellHeight = cellHeight,
                cellWidth = cellWidth
        )
        isGameRunning = true

        // Draw the Xs and Os
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                val cellCenter = Offset(
                        x = col * cellSize.width + cellSize.width / 2f,
                        y = row * cellSize.height + cellSize.height / 2f
                )
                val animation = animations[row][col]
                when (cellState[row][col]) {
                    CellState.X -> drawX(cellSize, animation.value, cellCenter)
                    CellState.O -> drawO(cellSize, animation.value, cellCenter)
                    else -> Unit
                }
            }
        }
    }
}

private fun DrawScope.drawX(
        cellSize: Size,
        progress: Float,
        center: Offset,
        sizeRatio: Float = 0.6f
) {
    val halfRatio = sizeRatio / 2f
    val xPath = Path().apply {
        val x1 = center.x - cellSize.width * halfRatio
        val y1 = center.y - cellSize.height * halfRatio
        val x2 = center.x + cellSize.width * halfRatio
        val y2 = center.y - cellSize.height * halfRatio

        // Animate drawing the first diagonal
        if (progress <= 1f) {
            moveTo(x1, y1)
            lineTo(
                    x1 + cellSize.width * sizeRatio * progress,
                    y1 + (cellSize.height * sizeRatio) * progress
            )
        } else {
            // Draw the diagonal completely
            moveTo(x1, y1)
            lineTo(x1 + cellSize.width * sizeRatio, y1 + cellSize.height * sizeRatio)

            // Draw the anti diagonal based on progress in the second half
            val progress2 = progress - 1f
            moveTo(x2, y2)
            lineTo(
                    x2 - cellSize.width * sizeRatio * progress2,
                    y2 + (cellSize.height * sizeRatio) * progress2
            )
        }
    }
    drawPath(
            path = xPath,
            color = Color.Green,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawO(
        cellSize: Size,
        angleRatio: Float,
        center: Offset,
        diameter: Float = maxOf(cellSize.width, cellSize.height),
        sizeRatio: Float = 0.7f
) {
    val radius = diameter * sizeRatio
    val circleSize = Size(radius, radius)
    val arcRectLeft = center.x - radius / 2f
    val arcRectTop = center.y - radius / 2f

    drawArc(
            color = Color.Blue,
            startAngle = 0f,
            sweepAngle = 360f * angleRatio,
            useCenter = false,
            size = circleSize,
            style = Stroke(
                    width = 6.dp.toPx(), cap = StrokeCap.Round
            ),
            topLeft = Offset(arcRectLeft, arcRectTop)
    )
}

private fun DrawScope.drawGrid(
        gridSize: Int,
        gridHeight: Float,
        gridWidth: Float,
        cellHeight: Float,
        cellWidth: Float
) {
    for (i in 1 until gridSize) {
        val horizontalLinePath = Path().apply {
            moveTo(0f, i * cellHeight)
            lineTo(gridWidth, i * cellHeight)
        }
        drawPath(
                path = horizontalLinePath,
                color = Color.Black,
                style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                )
        )
    }
    for (i in 1 until gridSize) {
        val verticalLinePath = Path().apply {
            moveTo(i * cellWidth, 0f)
            lineTo(i * cellWidth, gridHeight)
        }
        drawPath(
                path = verticalLinePath,
                color = Color.Black,
                style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                )
        )
    }
}

private fun emptyGameBoard(gridSize: Int) = Array(gridSize) { IntArray(gridSize) { 0 } }

private fun emptyCellState(gridSize: Int) = Array(gridSize) { Array(gridSize) { CellState.EMPTY } }

private fun updateCellState(
        cellState: Array<Array<CellState>>,
        row: Int,
        col: Int,
        state: CellState
): Array<Array<CellState>> {
    val newCellState = cellState.copyOf()
    newCellState[row][col] = state
    return newCellState
}

private fun emptyAnimations(gridSize: Int): ArrayList<ArrayList<Animatable<Float, AnimationVector1D>>> {
    val arrayList = arrayListOf<ArrayList<Animatable<Float, AnimationVector1D>>>()
    for (i in 0 until gridSize) {
        arrayList.add(arrayListOf())
        for (j in 0 until gridSize) {
            arrayList[i].add(Animatable(0f))
        }
    }
    return arrayList
}

private fun CoroutineScope.animate(animatable: Animatable<Float, AnimationVector1D>, targetValue: Float) {
    launch {
        animatable.animateTo(
                targetValue = targetValue,
                animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearEasing
                )
        )
    }
}

fun checkForWin(gameBoard: Array<IntArray>, gridSize: Int): Boolean {
    val rowSums = IntArray(gridSize)
    val colSums = IntArray(gridSize)
    var mainDiagonalSum = 0
    var antiDiagonalSum = 0

    for (row in 0 until gridSize) {
        for (col in 0 until gridSize) {
            val cellValue = gameBoard[row][col]
            rowSums[row] += cellValue
            colSums[col] += cellValue

            // Calculate sums for diagonals
            if (row == col) {
                mainDiagonalSum += cellValue
            }
            if (row + col == gridSize - 1) {
                antiDiagonalSum += cellValue
            }
        }
    }

    // Check rows and columns
    for (i in 0 until gridSize) {
        if (abs(rowSums[i]) == gridSize || abs(colSums[i]) == gridSize) {
            return true
        }
    }

    // Check diagonals
    return abs(mainDiagonalSum) == gridSize || abs(antiDiagonalSum) == gridSize
}