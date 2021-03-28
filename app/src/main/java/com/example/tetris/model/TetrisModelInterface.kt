package com.example.tetris.model

interface TetrisModelInterface {
  var score: Int
  var nextBlocks: Array<Int>
  var holdBlock: Int

  fun addBlock()
  fun moveBlock()
  fun getField(): Array<Array<Int>>
}