package com.example.tetris.block

class StraightBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 1),
    arrayOf(6, 1),
    arrayOf(7, 1),
    arrayOf(8, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 1
) : BlockInterface {
  override fun rotate() {

  }
}