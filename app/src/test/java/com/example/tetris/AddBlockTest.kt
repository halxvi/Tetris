package com.example.tetris

import com.example.tetris.block.*
import com.example.tetris.model.Field
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class AddBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { (block: BlockInterface) -> Field(block) }
      }
    )
  }

  @Test
  fun addStraightBlock() {
    val field: Field by inject { parametersOf(StraightBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..8) {
      expectedBlocks[1][i] = 1
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addSquareBlock() {
    val field: Field by inject { parametersOf(SquareBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..6) {
      expectedBlocks[0][i] = 2
      expectedBlocks[1][i] = 2
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addZBlock() {
    val field: Field by inject { parametersOf(ZBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..6) {
      expectedBlocks[0][i] = 3
      expectedBlocks[1][i + 1] = 3
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addZReverseBlock() {
    val field: Field by inject { parametersOf(ZReverseBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..6) {
      expectedBlocks[0][i + 1] = 4
      expectedBlocks[1][i] = 4
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addLBlock() {
    val field: Field by inject { parametersOf(LBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks[0][7] = 5
    for (i in 5..7) {
      expectedBlocks[1][i] = 5
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addLReverseBlock() {
    val field: Field by inject { parametersOf(LReverseBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks[0][5] = 6
    for (i in 5..7) {
      expectedBlocks[1][i] = 6
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  @Test
  fun addTBlock() {
    val field: Field by inject { parametersOf(TBlock()) }
    field.addBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks[0][6] = 7
    for (i in 5..7) {
      expectedBlocks[1][i] = 7
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, field.blocks)
  }

  private fun addWallToBlocks(array: Array<Array<Int>>): Array<Array<Int>> {
    for (n in 0..21) {
      array[n][0] = -1
      array[n][11] = -1
    }
    for (i in 2..10) array[21][i] = -1
    return array
  }
}