package com.example.tetris.block

class ZBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 2),
    arrayOf(6, 2),
    arrayOf(6, 3),
    arrayOf(7, 3)
  ),
  override var direction: Int = 0,
  override val type: Int = 3
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {}
}