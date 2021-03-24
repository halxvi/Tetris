package com.example.tetris.model

class TetrisModelRepository : TetrisModelRepositoryInterface {
  override var fields: Array<Array<Int>> =
    Array(22) { Array<Int>(12) { 0 } }
  override var nextBlocks: MutableList<Int> = MutableList(3) { 0 }
  override var score: Int = 0
  override var gameover: Boolean = false
}