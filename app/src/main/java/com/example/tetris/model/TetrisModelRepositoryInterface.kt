package com.example.tetris.model

interface TetrisModelRepositoryInterface {
  var fields: Array<Array<Int>>
  var nextBlocks: MutableList<Int>
  var score: Int
  var gameover: Boolean
}