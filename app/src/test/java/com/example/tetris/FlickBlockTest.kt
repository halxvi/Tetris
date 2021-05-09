package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
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
        single { (selectedBlocks: BlockInterface) -> Field(selectedBlocks) }
      }
    )
  }

  @Test
  fun canFlickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(6, 3),
            arrayOf(7, 3),
            arrayOf(8, 3),
            arrayOf(9, 3)
          )
        )
      )
    }
    assertTrue(field.canFlickToRight())
  }

  @Test
  fun cantFlickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(7, 3),
            arrayOf(8, 3),
            arrayOf(9, 3),
            arrayOf(10, 3)
          )
        )
      )
    }
    assertFalse(field.canFlickToRight())
  }

  @Test
  fun flickToRight() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(6, 3),
            arrayOf(7, 3),
            arrayOf(8, 3),
            arrayOf(9, 3)
          )
        )
      )
    }
    field.flickToRight()
    val expectedBlocks = Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, field.selectedBlock.direction, 7, 2)
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
            arrayOf(2, 3),
            arrayOf(3, 3),
            arrayOf(4, 3),
            arrayOf(5, 3)
          )
        )
      )
    }
    assertTrue(field.canFlickToLeft())
  }

  @Test
  fun cantFlickToLeft() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(1, 3),
            arrayOf(2, 3),
            arrayOf(3, 3),
            arrayOf(4, 3)
          )
        )
      )
    }
    assertFalse(field.canFlickToLeft())
  }

  @Test
  fun flickToLeft() {
    val field: Field by inject {
      parametersOf(
        StraightBlock(
          arrayOf(
            arrayOf(2, 3),
            arrayOf(3, 3),
            arrayOf(4, 3),
            arrayOf(5, 3)
          )
        )
      )
    }
    field.flickToLeft()
    val expectedBlocks = Array(24) { Array<Int>(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, field.selectedBlock.type, field.selectedBlock.direction, 1, 2)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
  }
}