package com.example.tetris.block

class InitBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(1, 0),
    arrayOf(1, 0),
    arrayOf(1, 0),
    arrayOf(1, 0)
  ),
  override var direction: Int = 0,
  override val type: Int = 0
) : BlockInterface {
  override fun rotate() {}
}