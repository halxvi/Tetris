package com.example.tetris

import com.example.tetris.model.TetrisModel
import com.example.tetris.repository.TetrisModelRepository
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class AddBlockTest : KoinTest {

  private val model: TetrisModel by inject()

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { TetrisModelRepository() }
        factory { TetrisModel(get()) }
      })
  }

  @Test
  fun addStraightBlock() {
    model.nextBlocks = MutableList<Int>(3) { 1 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..3) {
      expectedArray[1][i] = 8
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addSquareBlock() {
    model.nextBlocks = MutableList<Int>(3) { 2 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i] = 9
      expectedArray[1][i] = 9
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addZReverseBlock() {
    val model: TetrisModel by inject()
    model.nextBlocks = MutableList<Int>(3) { 3 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i + 1] = 10
      expectedArray[1][i] = 10
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addZBlock() {
    val model: TetrisModel by inject()
    model.nextBlocks = MutableList<Int>(3) { 4 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[0][i] = 11
      expectedArray[1][i + 1] = 11
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addLBlock() {
    val model: TetrisModel by inject()
    model.nextBlocks = MutableList<Int>(3) { 5 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][0] = 12
    for (i in 0..2) {
      expectedArray[1][i] = 12
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addLReverseBlock() {
    val model: TetrisModel by inject()
    model.nextBlocks = MutableList<Int>(3) { 6 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][2] = 13
    for (i in 0..2) {
      expectedArray[1][i] = 13
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun addTBlock() {
    val model: TetrisModel by inject()
    model.nextBlocks = MutableList<Int>(3) { 7 }
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    expectedArray[0][1] = 14
    for (i in 0..2) {
      expectedArray[1][i] = 14
    }
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }

  @Test
  fun notBlockSelected() {
    val model: TetrisModel by inject()
    model.isBlockSelected = true
    model.addBlock()
    val expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    assertArrayEquals(expectedArray, model.fields)
    assertTrue(model.isBlockSelected)
  }
}