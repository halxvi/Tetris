package com.example.tetris

interface TetrisInterface {
  var fields : Array<Array<Int>>
  var nextBlocks: MutableList<Int>
  fun addBlock()
}