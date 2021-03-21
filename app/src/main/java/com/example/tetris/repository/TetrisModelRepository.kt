package com.example.tetris.repository

import com.example.tetris.interfaces.TetrisModelRepositoryInterface

class TetrisModelRepository : TetrisModelRepositoryInterface {
  override var fields: Array<Array<Int>> =
    Array(23) { Array<Int>(10) { 0 } }
  override var nextBlocks: MutableList<Int> = MutableList(3) { 0 }
  override var score: Int = 0
  override var gameover: Boolean = false
}