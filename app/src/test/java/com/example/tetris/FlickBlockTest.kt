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
      insertBlock(expectedBlocks, field.selectedBlock.type, 7, 1)
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
}