package com.example.tetris.block

class InitBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 0),
    arrayOf(5, 0),
    arrayOf(5, 0),
    arrayOf(5, 0)
  ),
  override var direction: Int = 0,
  override val type: Int = 0
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {}
}