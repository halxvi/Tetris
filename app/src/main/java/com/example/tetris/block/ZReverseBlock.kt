package com.example.tetris.block

class ZReverseBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(6, 0),
    arrayOf(7, 0),
    arrayOf(5, 1),
    arrayOf(6, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 4
) : BlockInterface {
  override fun rotate() {

  }
}