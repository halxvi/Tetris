package com.example.tetris.model.block

interface BlockInterface {
  var coordinates: Array<Array<Int>>
  var direction: Int
  val type: Int

  fun rotate()
}