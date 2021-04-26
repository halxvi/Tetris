package com.example.tetris.block

class SquareBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 0),
    arrayOf(6, 0),
    arrayOf(5, 1),
    arrayOf(6, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 2
) : BlockInterface {
  override fun rotate() {

  }
}