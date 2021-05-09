package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.getTestBlock
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
    "1 | 0 | 5 | 3",
    "1 | 1 | 5 | 1",
    delimiter = '|'
  )
  fun moveBlock(blockType: Int, direction: Int, tx: Int, ty: Int) {
    val field: Field by inject {
      parametersOf(
        getTestBlock(blockType),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }

    if (direction != 0) {
      for (n in 1..direction) field.rotate()
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
        3
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

  @ParameterizedTest
  @CsvSource("1", "2", "3", "4", "5", "6", "7")
  fun moveBlockToBottom(type: Int) {
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        Array(24) { Array<Int>(12) { 0 } },
        mutableListOf(type, 0, 0),
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
        type,
        0,
        5,
        21
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @ParameterizedTest
  @CsvSource("1", "2", "3", "4", "5", "6", "7")
  fun moveBlockToBlock(type: Int) {
    val block = Array(24) { Array<Int>(12) { 0 } }
    block.apply {
      insertBlock(
        block,
        type,
        0,
        5,
        21
      )
    }
    val field: Field by inject {
      parametersOf(
        InitBlock(),
        block,
        mutableListOf(type, 0, 0),
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
        type,
        field.selectedBlock.direction,
        5,
        if (type == 1) 20 else 19
      )
      insertBlock(
        expectedBlocks,
        type,
        field.selectedBlock.direction,
        5,
        21
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }
}
