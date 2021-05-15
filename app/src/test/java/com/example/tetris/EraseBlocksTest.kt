package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.InitBlock
import com.example.tetris.model.Tetris
import org.junit.Assert.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class EraseBlocksTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   selectedBlock: BlockInterface,
                   blocks: Array<Array<Int>>
                 ) ->
          Tetris(
            selectedBlock,
            blocks
          )
        }
      })
  }

  @ParameterizedTest
  @CsvSource(
    "4", "5", "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun canFindErasableBlocks(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..10) blocks[i][n] = 1

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    assertEquals(i, tetris.findErasableBlocks())
  }

  @ParameterizedTest
  @CsvSource(
    "4", "5", "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun cantFindErasableBlocks(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..9) blocks[i][n] = 1

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    assertEquals(-1, tetris.findErasableBlocks())
  }

  @ParameterizedTest
  @CsvSource(
    "4", "5", "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun eraseBlocks(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..10) blocks[i][n] = 1

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    val expectedBlocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    tetris.eraseBlocks()
    assertArrayEquals(expectedBlocks, tetris.blocks)
  }

  @ParameterizedTest
  @CsvSource(
    "5", "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun eraseBlocksAndMoveBlocks(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..10) blocks[i][n] = 1
    for (n in 1..9) blocks[i - 1][n] = 1

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    val expectedBlocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..9) expectedBlocks[i - 1][n] = 1
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    tetris.eraseBlocks()
    assertArrayEquals(expectedBlocks, tetris.blocks)
  }

  @ParameterizedTest
  @CsvSource(
    "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun eraseAllBlocks(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    for (n in 1..10) blocks[i][n] = 1
    for (n in 1..10) blocks[i - 1][n] = 2
    for (n in 1..10) blocks[i - 2][n] = 2

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    val expectedBlocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    tetris.eraseBlocks()
    assertArrayEquals(expectedBlocks, tetris.blocks)
  }
}