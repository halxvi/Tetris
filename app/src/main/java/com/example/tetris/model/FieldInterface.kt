package com.example.tetris.field

interface FieldInterface {
  fun addBlock()
  fun moveBlock()
  fun checkErasableBlock()
  fun checkGameover()
  fun combineField(): Array<Array<Int>>
}