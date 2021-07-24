package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.ZReverseBlock
import com.example.tetris.model.Tetris
import org.junit.Assert.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.inject
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

class RotateZReverseBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   coordinates: Array<Array<Int>>,
                   direction: Int
                 ) ->
          ZReverseBlock(
            coordinates,
            direction
          )
        }
        single { (
                   selectedBlock: BlockInterface
                 ) ->
          Tetris(
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
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(zReverseBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )
  fun canRotateD1(x: Int) {
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(zReverseBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "8 | 10 | 20",
    "7 | 9 | 20",
    "6 | 8 | 20",
    "5 | 7 | 20",
    "8 | 9 | 15",
    "7 | 8 | 15",
    "6 | 7 | 15",
    "5 | 6 | 15",
    "4 | 6 | 20",
    "3 | 5 | 20",
    "2 | 4 | 20",
    "1 | 3 | 20",
    "4 | 4 | 16",
    "3 | 3 | 16",
    "2 | 2 | 16",
    "1 | 1 | 16",
    "4 | 4 | 15",
    "3 | 3 | 15",
    "2 | 2 | 15",
    "1 | 1 | 15",
    delimiter = '|'
  )
  fun cantRotateD0Cwb(x: Int, tx: Int, ty: Int) {
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(zReverseBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "9 | 9 | 20",
    "8 | 8 | 20",
    "7 | 7 | 20",
    "6 | 6 | 20",
    "9 | 8 | 20",
    "8 | 7 | 20",
    "7 | 6 | 20",
    "6 | 5 | 20",
    "5 | 5 | 20",
    "4 | 4 | 20",
    "3 | 3 | 20",
    "2 | 2 | 20",
    "1 | 1 | 20",
    "5 | 7 | 16",
    "4 | 6 | 16",
    "3 | 5 | 16",
    "2 | 4 | 16",
    "1 | 3 | 16",
    delimiter = '|'
  )
  fun cantRotateD1Cwb(x: Int, tx: Int, ty: Int) {
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(zReverseBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "8 | 9",
    "7 | 8",
    "6 | 7",
    "5 | 6",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD0(x: Int, tx: Int) {
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        0
      )
    }
    val tetris: Tetris by inject { parametersOf(zReverseBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 4, 1, tx, 18)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
    assertEquals(tetris.selectedBlock.direction, 1)
  }

  @ParameterizedTest
  @CsvSource(
    "9 | 8",
    "8 | 7",
    "7 | 6",
    "6 | 5",
    "5 | 5",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD1(x: Int, tx: Int) {
    val zReverseBlock: ZReverseBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val tetris: Tetris by inject { parametersOf(zReverseBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 4, 0, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
    assertEquals(tetris.selectedBlock.direction, 0)
  }
}