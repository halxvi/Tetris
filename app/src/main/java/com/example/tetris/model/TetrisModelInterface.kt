package com.example.tetris.model

interface TetrisModelInterface {
  var score: Int

  fun addBlock()
  fun moveBlock()
  fun getField(): Array<Array<Int>>
}