package com.example.tetris.model

class TetrisModelRepository : TetrisModelRepositoryInterface {
  override var nextBlocks: Array<Int> = Array(3) { 0 }
  override var score: Int = 0
  override var gameover: Boolean = false
}