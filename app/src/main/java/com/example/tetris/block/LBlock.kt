package com.example.tetris.block

class LBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(7, 0),
    arrayOf(5, 1),
    arrayOf(6, 1),
    arrayOf(7, 1)
  ),
  override var direction: Int = 0,
  override val type: Int = 5
) : BlockInterface {
  override fun rotate() {

  }
}