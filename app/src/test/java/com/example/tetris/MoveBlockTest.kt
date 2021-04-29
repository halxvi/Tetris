package com.example.tetris

import com.example.tetris.block.BlockInterface
import com.example.tetris.block.InitBlock
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
import kotlin.random.Random

class MoveBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { (
                   selectedBlocks: BlockInterface,
                   blocks: Array<Array<Int>>,
                   nextBlocks: MutableList<Int>,
                   heldBlock: Int,
                   random: Random
                 ) ->
          Field(
            selectedBlocks,
            blocks,
            nextBlocks,
            heldBlock,
            random
          )
        }
      }
    )
  }

  @Test
  fun moveBlock() {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(22) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        0,
        Random
      )
    }
    field.addBlock()
    field.moveBlock()
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 1)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun canMoveBlock() {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(22) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        0,
        Random
      )
    }
    assertTrue(field.checkMoveBlock())
  }

  @Test
  fun cantMoveBlockWithCollapse() {
    var blocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    blocks.apply { insertBlock(blocks, 1, 1) }
    val field: Field by inject {
      parametersOf(
        StraightBlock(),
        blocks,
        mutableListOf(0, 0, 0),
        0,
        Random
      )
    }
    assertFalse(field.checkMoveBlock())
  }

  @Test
  fun cantMoveBlockWithBottom() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(5, 20),
            arrayOf(6, 20),
            arrayOf(7, 20),
            arrayOf(8, 20)
          )
        ),
        Array(22) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        0,
        Random
      )
    }
    assertFalse(field.checkMoveBlock())
  }

  @Test
  fun moveBlockToBottom() {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(22) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        0,
        Random
      )
    }
    field.addBlock()
    while (field.checkMoveBlock()) {
      field.moveBlock()
    }
    var expectedBlocks: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        field.selectedBlock.type,
        if (field.selectedBlock.type == 1) 20 else 19
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  private fun addWallToBlocks(array: Array<Array<Int>>): Array<Array<Int>> {
    for (n in 0..21) {
      array[n][0] = -1
      array[n][11] = -1
    }
    for (i in 2..10) array[21][i] = -1
    return array
  }

  private fun insertBlock(
    expectedBlocks: Array<Array<Int>>,
    type: Int,
    targetY: Int
  ): Array<Array<Int>> {
    when (type) {
      1 -> for (i in 5..8) {
        expectedBlocks[targetY][i] = 1
      }
      2 -> for (i in 5..6) {
        expectedBlocks[targetY][i] = 2
        expectedBlocks[targetY + 1][i] = 2
      }
      3 -> for (i in 5..6) {
        expectedBlocks[targetY][i] = 3
        expectedBlocks[targetY + 1][i + 1] = 3
      }
      4 -> for (i in 5..6) {
        expectedBlocks[targetY][i + 1] = 4
        expectedBlocks[targetY + 1][i] = 4
      }
      5 -> {
        expectedBlocks[targetY][7] = 5
        for (i in 5..7) {
          expectedBlocks[targetY + 1][i] = 5
        }
      }
      6 -> {
        expectedBlocks[targetY][5] = 6
        for (i in 5..7) {
          expectedBlocks[targetY + 1][i] = 6
        }
      }
      7 -> {
        expectedBlocks[targetY][6] = 7
        for (i in 5..7) {
          expectedBlocks[targetY + 1][i] = 7
        }
      }
    }
    return expectedBlocks
  }
}
