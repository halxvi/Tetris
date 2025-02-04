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

class ScoreTest : KoinTest {
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
  fun addScore(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(25) { Array(12) { 0 } }
    for (n in 1..10) blocks[i][n] = 1

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    tetris.eraseBlocks()
    assertEquals(10, tetris.score)
  }

  @ParameterizedTest
  @CsvSource(
    "6", "7", "8",
    "9", "10", "11", "12", "13",
    "14", "15", "16", "17", "18",
    "19", "20", "21", "22"
  )
  fun eraseAllBlocksAndAddScore(i: Int) {
    val blocks: Array<Array<Int>> =
      Array(25) { Array(12) { 0 } }
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
      Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    tetris.eraseBlocks()
    assertEquals(30, tetris.score)
  }
}