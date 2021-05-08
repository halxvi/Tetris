package com.example.tetris.model

class Tetris(
  private val field: Field
) {
  var gameover: Boolean = false
  var gameSpeed: Double = 500.0
  var score: Int = 0

  fun addBlock() {
    field.addBlock()
  }

  fun moveBlock() {
    field.moveBlock()
  }

  fun updateScore() {
    score = field.score
  }

  fun combineBlocks(): Array<Array<Int>> {
    return field.combineBlocks()
  }

  fun onSpeedUp() {
    val speed = 10
    for (i in 0..speed) {
    }
  }
}