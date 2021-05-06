package com.example.tetris.block

interface BlockInterface {
  var coordinates: Array<Array<Int>>
  var direction: Int
  val type: Int

  fun rotate(blocks: Array<Array<Int>>)
}