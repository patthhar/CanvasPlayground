package me.darthwithap.android.canvasplayground.weight_scale

sealed class LineType {
  object Normal : LineType()
  object FiveStep : LineType()
  object TenStep : LineType()
}