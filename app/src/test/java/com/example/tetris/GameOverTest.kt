package com.example.tetris

import com.example.tetris.Utilities.Companion.insertBlock
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

class GameOverTest : KoinTest {
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
    "1", "2", "3", "4", "5", "6", "7"
  )
  fun isGameOver(x: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    blocks.apply {
      insertBlock(blocks, 1, 0, x, 3)
      insertBlock(blocks, 1, 0, x, 2)
    }

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    assertTrue(tetris.isGameover())
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7"
  )
  fun `isn'tGameOver`(x: Int) {
    val blocks: Array<Array<Int>> =
      Array(24) { Array<Int>(12) { 0 } }
    blocks.apply {
      insertBlock(blocks, 1, 0, x, 4)
      insertBlock(blocks, 1, 0, x, 2)
    }

    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        blocks
      )
    }

    assertFalse(tetris.isGameover())
  }
}