package com.example.tetris

import org.junit.Test

import org.junit.Assert.*

class AddBlockTest {
  @Test
  fun addStraightBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 1 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..3) {
      expectedArray[1][i] = 8
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun notBlockSelected(){
    val tetris = Tetris()
    tetris.isBlockSelected = true
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }
}