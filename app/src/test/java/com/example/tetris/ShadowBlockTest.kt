package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertShadowBlock
import com.example.tetris.Utilities.Companion.getTestBlock
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.*
import com.example.tetris.model.Tetris
import org.junit.Assert.assertArrayEquals
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class ShadowBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   selectedBlock: BlockInterface
                 ) ->
          Tetris(
            selectedBlock
          )
        }
      }
    )
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7"
  )
  fun createShadowBlock(type: Int) {
    val tetris: Tetris by inject {
      parametersOf(
        getTestBlock(type)
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks,type,0,5,2)
      insertShadowBlock(expectedBlocks, type, 0, 5, 22)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineShadowBlock(tetris.combineBlocks()))
  }
}