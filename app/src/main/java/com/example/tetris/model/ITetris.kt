package com.example.tetris.model

interface ITetris {
  var blocks: Array<Array<Int>>
  var nextBlocks: MutableList<Int>
  var score:Int
  var level:Int

  fun combineAllBlocks(): Array<Array<Int>>
  fun moveBlock()
  fun canMoveBlock(coordinates: Array<Array<Int>>): Boolean
  fun flickToRight()
  fun flickToLeft()
  fun rotate()
  fun eraseBlocks()
  fun isGameover():Boolean
  fun startGame()
  fun fallDownBlock()
  fun restart()
}