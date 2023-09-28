package me.darthwithap.android.canvasplayground.clock

sealed class MarkLineType {
  object NormalMarkLine : MarkLineType()
  object HourMarkMarkLine : MarkLineType()
  object NoMarkLine : MarkLineType()
}