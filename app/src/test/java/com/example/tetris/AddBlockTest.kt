package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.model.Field
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
          Field()
        }
      })
  }

  @ParameterizedTest
  @CsvSource("1", "2", "3", "4", "5", "6", "7")
  fun addBlock(blockType: Int) {
    val field: Field by inject()
    field.nextBlocks[0] = blockType
    field.addBlock()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        field.selectedBlock.type,
        field.selectedBlock.direction,
        5,
        2
      )
      addWallToBlocks(expectedBlocks)
    }
    assertEquals(field.selectedBlock.type, blockType)
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }
}