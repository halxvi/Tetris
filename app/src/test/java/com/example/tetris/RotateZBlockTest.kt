package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.ZBlock
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

class RotateZBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   coordinates: Array<Array<Int>>,
                   direction: Int
                 ) ->
          ZBlock(
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
    "1", "2", "3", "4", "5", "6", "7", "8"
  )
  fun canRotateD0(x: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(zBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )
  fun canRotateD1(x: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(zBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "8 | 10 | 6",
    "7 | 9 | 6",
    "6 | 8 | 6",
    "5 | 5 | 10",
    "4 | 4 | 10",
    "3 | 3 | 10",
    "2 | 2 | 10",
    "1 | 1 | 10",
    delimiter = '|'
  )
  fun cantRotateD0Cwb(x: Int, tx: Int, ty: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 9),
          arrayOf(x + 1, 9),
          arrayOf(x + 1, 10),
          arrayOf(x + 2, 10)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(zBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "9 | 8 | 17",
    "8 | 7 | 17",
    "7 | 6 | 17",
    "6 | 5 | 17",
    "5 | 4 | 17",
    "4 | 6 | 17",
    "3 | 5 | 17",
    "2 | 4 | 17",
    "1 | 3 | 17",
    delimiter = '|'
  )
  fun cantRotateD1Cwb(x: Int, tx: Int, ty: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(zBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "8 | 9",
    "7 | 8",
    "6 | 7",
    "5 | 5",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD0(x: Int, tx: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val field: Field by inject { parametersOf(zBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 3, 1, tx, 18)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 1)
  }

  @ParameterizedTest
  @CsvSource(
    "9 | 8",
    "8 | 7",
    "7 | 6",
    "6 | 5",
    "5 | 4",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD1(x: Int, tx: Int) {
    val zBlock: ZBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val field: Field by inject { parametersOf(zBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 3, 0, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 0)
  }
}