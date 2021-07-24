package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.model.Tetris
import org.junit.Assert.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class AddBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single {
          Tetris()
        }
      })
  }

  @ParameterizedTest
  @CsvSource("1", "2", "3", "4", "5", "6", "7")
  fun addBlock(blockType: Int) {
    val tetris: Tetris by inject()
    tetris.nextBlocks[0] = blockType
    tetris.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        tetris.selectedBlock.type,
        tetris.selectedBlock.direction,
        5,
        2
      )
      addWallToBlocks(expectedBlocks)
    }
    assertEquals(tetris.selectedBlock.type, blockType)
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
  }
}