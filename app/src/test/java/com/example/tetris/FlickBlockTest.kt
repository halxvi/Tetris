package com.example.tetris

import com.example.tetris.block.BlockInterface
import com.example.tetris.block.StraightBlock
import com.example.tetris.model.Field
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class FlickBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { (
                   selectedBlocks: BlockInterface
                 ) ->
          Field(
            selectedBlocks
          )
        }
      }
    )
  }

  @Test
  fun canFlickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(6, 1),
            arrayOf(7, 1),
            arrayOf(8, 1),
            arrayOf(9, 1)
          )
        )
      )
    }
    assertTrue(field.checkFlickToRight())
  }

  @Test
  fun cantFlickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(7, 1),
            arrayOf(8, 1),
            arrayOf(9, 1),
            arrayOf(10, 1)
          )
        )
      )
    }
    assertFalse(field.checkFlickToRight())
  }

  @Test
  fun flickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(6, 1),
            arrayOf(7, 1),
            arrayOf(8, 1),
            arrayOf(9, 1)
          )
        )
      )
    }
    field.flickToRight()
    val expectedBlocks = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 7, 1)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }

  @Test
  fun canFlickToLeft() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(2, 1),
            arrayOf(3, 1),
            arrayOf(4, 1),
            arrayOf(5, 1)
          )
        )
      )
    }
    assertTrue(field.checkFlickToLeft())
  }

  @Test
  fun cantFlickToLeft() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(1, 1),
            arrayOf(2, 1),
            arrayOf(3, 1),
            arrayOf(4, 1)
          )
        )
      )
    }
    assertFalse(field.checkFlickToLeft())
  }

  @Test
  fun flickToLeft() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(2, 1),
            arrayOf(3, 1),
            arrayOf(4, 1),
            arrayOf(5, 1)
          )
        )
      )
    }
    field.flickToLeft()
    val expectedBlocks = Array(22) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, 1, 1)
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
    x: Int,
    y: Int
  ): Array<Array<Int>> {
    when (type) {
      1 -> for (i in x..x + 3) {
        expectedBlocks[y][i] = 1
      }
      2 -> for (i in x..x + 1) {
        expectedBlocks[y][i] = 2
        expectedBlocks[y + 1][i] = 2
      }
      3 -> for (i in x..x + 1) {
        expectedBlocks[y][i] = 3
        expectedBlocks[y + 1][i + 1] = 3
      }
      4 -> for (i in x..x + 1) {
        expectedBlocks[y][i + 1] = 4
        expectedBlocks[y + 1][i] = 4
      }
      5 -> {
        expectedBlocks[y][x + 3] = 5
        for (i in x..x + 3) {
          expectedBlocks[y + 1][i] = 5
        }
      }
      6 -> {
        expectedBlocks[y][x] = 6
        for (i in x..x + 3) {
          expectedBlocks[y + 1][x + i] = 6
        }
      }
      7 -> {
        expectedBlocks[y][x + 1] = 7
        for (i in x..x + 3) {
          expectedBlocks[y + 1][x + i] = 7
        }
      }
    }
    return expectedBlocks
  }
}