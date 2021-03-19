package com.example.tetris.interfaces

interface TetrisModelRepositoryInterface {
  var fields: Array<Array<Int>>
  var nextBlocks: MutableList<Int>
  var score: Int
  var gameover: Boolean
}