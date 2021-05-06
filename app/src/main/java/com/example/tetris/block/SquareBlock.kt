package com.example.tetris.block

class SquareBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 2),
    arrayOf(6, 2),
    arrayOf(5, 3),
    arrayOf(6, 3)
  ),
  override var direction: Int = 0,
  override val type: Int = 2
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {}
}