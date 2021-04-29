package com.example.tetris

import com.example.tetris.block.BlockInterface
import com.example.tetris.block.StraightBlock
import com.example.tetris.model.Field
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class MoveBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { (block: BlockInterface) -> Field(block) }
      }
    )
  }

  @Test
  fun moveBlock() {
    val field: Field by inject { parametersOf(StraightBlock()) }
    field.moveBlock()
    val combinedBlocks = field.combineBlocks()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..8) {
      expectedBlocks[2][i] = 1
    }
    expectedBlocks = addWallToBlocks(expectedBlocks)
    assertArrayEquals(expectedBlocks, combinedBlocks)
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
