package com.example.tetris.model

interface TetrisModelRepositoryInterface {
  var nextBlocks: Array<Int>
  var score: Int
  var gameover: Boolean
}