package com.example.tetris.block

class ZBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 0),
    arrayOf(6, 0),
    arrayOf(6, 1),
    arrayOf(7, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 3
) : BlockInterface {
  override fun rotate() {

  }
}