package me.darthwithap.android.canvasplayground.tic_tac_toe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(
        modifier: Modifier = Modifier,
        canvasPadding: Dp = 16.dp
) {
    var winner by remember {
        mutableStateOf<Turn?>(null)
    }
    BoxWithConstraints(
            modifier = modifier.fillMaxSize()
    ) {
        val height = constraints.maxHeight.toFloat()
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TicTacToe(
                    height = height,
                    canvasPadding = canvasPadding,
                    onPlayerWin = {
                        if (winner == null)
                            winner = it
                    },
                    onNewRound = { winner = null },
            )
            Spacer(modifier = Modifier.height(50.dp))
            winner?.let {
                Text(
                        text = if (it != Turn.None) "Player ${it.symbol} has won!" else "Draw",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                )
            }
        }
    }
}