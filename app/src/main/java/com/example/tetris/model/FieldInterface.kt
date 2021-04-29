package com.example.tetris.model

interface FieldInterface {
  fun addBlock()
  fun moveBlock()
  fun checkErasableBlock()
  fun checkGameover()
  fun combineField(): Array<Array<Int>>
}