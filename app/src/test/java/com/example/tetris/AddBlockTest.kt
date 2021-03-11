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
  fun addSquareBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 2 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i] = 9
      expectedArray[1][i] = 9
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun addZReverseBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 3 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i + 1] = 10
      expectedArray[1][i] = 10
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun addZBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 4 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i] = 11
      expectedArray[1][i + 1] = 11
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun addLBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 5 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][0] = 12
    for (i in 0..2) {
      expectedArray[1][i] = 12
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun addLReverseBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 6 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][2] = 13
    for (i in 0..2) {
      expectedArray[1][i] = 13
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun addTBlock() {
    val tetris = Tetris()
    tetris.nextBlocks = MutableList<Int>(3) { 7 }
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][1] = 14
    for (i in 0..2) {
      expectedArray[1][i] = 14
    }
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }

  @Test
  fun notBlockSelected() {
    val tetris = Tetris()
    tetris.isBlockSelected = true
    tetris.addBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    assertArrayEquals(expectedArray, tetris.fields)
    assertTrue(tetris.isBlockSelected)
  }
}