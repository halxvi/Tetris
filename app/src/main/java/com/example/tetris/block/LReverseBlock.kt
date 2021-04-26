package com.example.tetris.block

class LReverseBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 0),
    arrayOf(5, 1),
    arrayOf(6, 1),
    arrayOf(7, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 6
) : BlockInterface {
  override fun rotate() {

  }
}