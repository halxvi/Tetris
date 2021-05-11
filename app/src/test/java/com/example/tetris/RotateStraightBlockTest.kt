package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.StraightBlock
import com.example.tetris.model.Field
import org.junit.Assert.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class RotateStraightBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   coordinates: Array<Array<Int>>,
                   direction: Int
                 ) ->
          StraightBlock(
            coordinates,
            direction
          )
        }
        single { (
                   selectedBlock: BlockInterface
                 ) ->
          Field(
            selectedBlock
          )
        }
      })
  }

  //d0 = direction 0
  //cwb = collisionWithBlock

  @ParameterizedTest
  @CsvSource(
    "7 | 8 | 9 | 10",
    "6 | 7 | 8 | 9",
    "5 | 6 | 7 | 8",
    "4 | 5 | 6 | 7",
    "3 | 4 | 5 | 6",
    "2 | 3 | 4 | 5",
    "1 | 2 | 3 | 4",
    delimiter = '|'
  )
  fun canRotateD0(
    x1: Int,
    x2: Int,
    x3: Int,
    x4: Int
  ) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x1, 4),
          arrayOf(x2, 4),
          arrayOf(x3, 4),
          arrayOf(x4, 4)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(straightBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
  )
  fun canRotateD1(x: Int) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 4),
          arrayOf(x, 5),
          arrayOf(x, 6),
          arrayOf(x, 7)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(straightBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 1 | 9 | 17",
    "7 | 1 | 8 | 17",
    "7 | 1 | 7 | 17",
    "7 | 0 | 7 | 18",
    "7 | 0 | 7 | 17",
    "7 | 0 | 7 | 16",
    "1 | 1 | 4 | 17",
    "1 | 1 | 3 | 17",
    "1 | 1 | 2 | 17",
    "1 | 0 | 1 | 18",
    "1 | 0 | 1 | 17",
    "1 | 0 | 1 | 16",
    delimiter = '|'
  )
  fun cantRotateD0Cwb(
    x: Int,
    direction: Int,
    tx: Int,
    ty: Int
  ) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20),
          arrayOf(x + 3, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, direction, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(straightBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "10 | 1 | 9 | 17",
    "10 | 1 | 8 | 17",
    "10 | 1 | 7 | 17",
    "10 | 0 | 6 | 18",
    "10 | 0 | 6 | 17",
    "10 | 0 | 6 | 16",
    "1 | 1 | 4 | 17",
    "1 | 1 | 3 | 17",
    "1 | 1 | 2 | 17",
    "1 | 0 | 2 | 18",
    "1 | 0 | 2 | 17",
    "1 | 0 | 2 | 16",
    delimiter = '|'
  )
  fun cantRotateD1Cwb(
    x: Int,
    direction: Int,
    tx: Int,
    ty: Int
  ) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 17),
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, direction, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(straightBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 8 | 9 | 10 | 10",
    "6 | 7 | 8 | 9 | 9",
    "5 | 6 | 7 | 8 | 5",
    "4 | 5 | 6 | 7 | 4",
    "3 | 4 | 5 | 6 | 3",
    "2 | 3 | 4 | 5 | 2",
    "1 | 2 | 3 | 4 | 1",
    delimiter = '|'
  )
  fun rotateD0(
    x1: Int,
    x2: Int,
    x3: Int,
    x4: Int,
    tx: Int
  ) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x1, 20),
          arrayOf(x2, 20),
          arrayOf(x3, 20),
          arrayOf(x4, 20)
        ),
        0
      )
    }
    val field: Field by inject { parametersOf(straightBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, 17)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 1)
  }

  @ParameterizedTest
  @CsvSource(
    "10 | 7",
    "9 | 6",
    "8 | 5",
    "7 | 4",
    "6 | 3",
    "5 | 5",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD1(x: Int, tx: Int) {
    val straightBlock: StraightBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 17),
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val field: Field by inject { parametersOf(straightBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 0)
  }
}