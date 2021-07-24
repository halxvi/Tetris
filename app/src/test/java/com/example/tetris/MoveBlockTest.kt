package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.getTestBlock
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.*
import com.example.tetris.model.Tetris
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
          Tetris(
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
    val tetris: Tetris by inject {
      parametersOf(
        getTestBlock(blockType),
        Array(25) { Array(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }

    if (direction != 0) {
      repeat(direction) { tetris.rotate() }
    }
    tetris.moveBlock()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
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
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
  }

  @ParameterizedTest
  @CsvSource(
    "true",
    "false"
  )
  fun canMoveBlock(isSelectedBlockStartPosition: Boolean) {
    val initBlock = if (isSelectedBlockStartPosition) {
      StraightBlock()
    } else {
      StraightBlock(
        arrayOf(
          arrayOf(5, 4),
          arrayOf(6, 4),
          arrayOf(7, 4),
          arrayOf(8, 4)
        )
      )
    }
    val tetris: Tetris by inject {
      parametersOf(
        initBlock,
        Array(25) { Array(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }

    assertTrue(tetris.canMoveBlock(tetris.selectedBlock.coordinates))
  }

  @ParameterizedTest
  @CsvSource(
    "true | 3",
    "false | 4",
    delimiter = '|'
  )
  fun cantMoveBlockWithCollision(isSelectedBlockStartPosition: Boolean, ty: Int) {
    val initBlock = if (isSelectedBlockStartPosition) {
      StraightBlock()
    } else {
      StraightBlock(
        arrayOf(
          arrayOf(5, 4),
          arrayOf(6, 4),
          arrayOf(7, 4),
          arrayOf(8, 4)
        )
      )
    }
    val blocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    blocks.apply {
      insertBlock(
        blocks,
        1,
        0,
        5,
        ty
      )
    }
    val tetris: Tetris by inject {
      parametersOf(
        initBlock,
        blocks,
        mutableListOf(0, 0, 0),
        Random
      )
    }

    assertFalse(tetris.canMoveBlock(tetris.selectedBlock.coordinates))
  }

  @Test
  fun cantMoveBlockWithBottom() {
    val tetris: Tetris by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(5, 23),
            arrayOf(6, 23),
            arrayOf(7, 23),
            arrayOf(8, 23)
          )
        ),
        Array(25) { Array(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random
      )
    }
    assertFalse(tetris.canMoveBlock(tetris.selectedBlock.coordinates))
  }

  @ParameterizedTest
  @CsvSource(
    "1 | 22",
    "2 | 22",
    "3 | 22",
    "4 | 22",
    "5 | 22",
    "6 | 22",
    "7 | 22",
    delimiter = '|'
  )
  fun moveBlockToBottom(type: Int, ty: Int) {
    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        Array(25) { Array(12) { 0 } },
        mutableListOf(type, 0, 0),
        Random
      )
    }
    tetris.addBlock()
    while (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
      tetris.moveBlock()
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        type,
        0,
        5,
        ty
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
  }

  @ParameterizedTest
  @CsvSource(
    "1 | 19",
    "2 | 20",
    "3 | 20",
    "4 | 20",
    "5 | 20",
    "6 | 20",
    "7 | 20",
    delimiter = '|'
  )
  fun moveBlockToBlock(type: Int, ty: Int) {
    val block = Array(25) { Array(12) { 0 } }
    block.apply {
      insertBlock(
        block,
        type,
        0,
        5,
        ty + 2
      )
    }
    val tetris: Tetris by inject {
      parametersOf(
        InitBlock(),
        block,
        mutableListOf(type, 0, 0),
        Random
      )
    }
    tetris.addBlock()
    while (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
      tetris.moveBlock()
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(
        expectedBlocks,
        type,
        tetris.selectedBlock.direction,
        5,
        if (type == 1) ty + 1 else ty
      )
      insertBlock(
        expectedBlocks,
        type,
        tetris.selectedBlock.direction,
        5,
        ty + 2
      )
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
  }
}
