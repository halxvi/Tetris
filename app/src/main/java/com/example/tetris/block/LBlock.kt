package com.example.tetris.block

class LBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(7, 2),
    arrayOf(5, 3),
    arrayOf(6, 3),
    arrayOf(7, 3)
  ),
  override var direction: Int = 0,
  override val type: Int = 5
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {}
}