package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.model.Field
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class AddBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single {
          Field()
        }
      }
    )
  }

  @Test
  fun addStraightBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 1
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addSquareBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 2
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addZBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 3
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addZReverseBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 4
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addLBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 5
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addLReverseBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 6
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun addTBlock() {
    val field: Field by inject()
    field.nextBlocks[0] = 7
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 5, 0)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }
}