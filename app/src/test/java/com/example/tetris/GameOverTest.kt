package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.getTestBlock
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
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
  @CsvSource("3")
  fun isGameOver(ty: Int) {
    val blocks: Array<Array<Int>> =
      Array(25) { Array(12) { 0 } }
    blocks.apply {
      insertBlock(blocks, 1, 0, 4, ty)
      addWallToBlocks(blocks)
    }

    val tetris: Tetris by inject {
      parametersOf(
        getTestBlock(1),
        blocks
      )
    }

    assertTrue(tetris.isGameover())
  }

  @ParameterizedTest
  @CsvSource(
    "true | true",
    "false | false",
    "true | false",
    delimiter = '|'
  )
  fun `isn'tGameOver`(canMoveBlock: Boolean, isInitPosition: Boolean) {
    val blocks: Array<Array<Int>> =
      Array(25) { Array(12) { 0 } }
    val ty = if (canMoveBlock) 21 else 4
    blocks.apply {
      insertBlock(blocks, 1, 0, 5, ty)
      addWallToBlocks(blocks)
    }

    val tetris: Tetris by inject {
      parametersOf(
        getTestBlock(1),
        blocks
      )
    }
    if (!isInitPosition) {
      repeat(2) { tetris.moveBlock() }
    }

    assertFalse(tetris.isGameover())
  }
}