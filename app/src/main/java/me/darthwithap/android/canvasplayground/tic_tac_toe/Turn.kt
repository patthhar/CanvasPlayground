package me.darthwithap.android.canvasplayground.tic_tac_toe

sealed class Turn(val symbol: String) {
    object O : Turn("O")
    object X : Turn("X")
    object None : Turn("None")

    operator fun not(): Turn {
        return if (this is X) O else X
    }
}