package com.example.tetris.model.block

class StraightBlock(
  override var coordinates: Array<Array<Int>>,
  override var direction: Int,
  override val type: Int
) : BlockInterface {
  override fun rotate() {

  }
}