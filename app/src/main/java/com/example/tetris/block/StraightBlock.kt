package com.example.tetris.block

class StraightBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 0),
    arrayOf(6, 0),
    arrayOf(7, 0),
    arrayOf(8, 0)
  ),
  override var direction: Int = 0,
  override val type: Int = 1
) : BlockInterface {
  override fun rotate() {

  }
}