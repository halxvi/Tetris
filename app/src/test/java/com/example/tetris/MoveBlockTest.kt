package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.*
import com.example.tetris.model.Field
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import kotlin.random.Random

class MoveBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   selectedBlocks: BlockInterface,
                   blocks: Array<Array<Int>>,
                   nextBlocks: MutableList<Int>,
                   random: Random
                 ) ->
          Field(
            selectedBlocks,
            blocks,
            nextBlocks,
            random
          )
        }
      }
    )
  }

  @ParameterizedTest
  @CsvSource(
    "1 | 0 | 5 | 4",
    "1 | 1 | 5 | 1",
    delimiter = '|'
  )
  fun moveBlock(blockType: Int, direction: Int, tx: Int, ty: Int) {
    val field: Field by inject {
      parametersOf(
        testBlock(blockType),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }

    if (direction != 0) {
      for (n in 1 .. direction) field.rotate()
    }
    field.moveBlock()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        blockType,
        direction,
        tx,
        ty
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun canMoveBlock() {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }
    assertTrue(field.canMoveBlock())
  }

  @Test
  fun cantMoveBlockWithCollision() {
    val blocks: Array<Array<Int>> = Array(24) { Array<Int>(12) { 0 } }
    blocks.apply {
      insertBlock(
        blocks,
        1,
        0,
        5,
        4
      )
    }
    val field: Field by inject {
      parametersOf(
        StraightBlock(),
        blocks,
        mutableListOf(0, 0, 0),
        Random
      )
    }
    assertFalse(field.canMoveBlock())
  }

  @Test
  fun cantMoveBlockWithBottom() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(5, 22),
            arrayOf(6, 22),
            arrayOf(7, 22),
            arrayOf(8, 22)
          )
        ),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }
    assertFalse(field.canMoveBlock())
  }

  @Test
  fun moveBlockToBottom() {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }
    field.addBlock()
    while (field.canMoveBlock()) {
      field.moveBlock()
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        field.selectedBlock.type,
        field.selectedBlock.direction,
        5,
        if (field.selectedBlock.type == 1) 22 else 21
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  private fun testBlock(blockType: Int): BlockInterface {
    return when (blockType) {
      1 -> StraightBlock()
      2 -> SquareBlock()
      3 -> ZBlock()
      4 -> ZReverseBlock()
      5 -> LBlock()
      6 -> LReverseBlock()
      7 -> TBlock()
      else -> SquareBlock()
    }
  }
}
