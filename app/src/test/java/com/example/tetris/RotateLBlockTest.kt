package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.LBlock
import com.example.tetris.model.Tetris
import org.junit.Assert.*
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class RotateLBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   coordinates: Array<Array<Int>>,
                   direction: Int
                 ) ->
          LBlock(
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
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )
  fun canRotateD1(x: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8"
  )
  fun canRotateD2(x: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 2, 18),
          arrayOf(x, 19)
        ),
        2
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )
  fun canRotateD3(x: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 8 | 16",
    "7 | 7 | 16",
    "1 | 2 | 16",
    "1 | 1 | 16",
    delimiter = '|'
  )
  fun cantRotateD0Cwb(x: Int, tx: Int, ty: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 8 | 18",
    "7 | 8 | 17",
    "7 | 3 | 19",
    "7 | 3 | 18",
    "1 | 3 | 18",
    "1 | 2 | 18",
    "1 | 2 | 17",
    delimiter = '|'
  )
  fun cantRotateD1Cwb(x: Int, tx: Int, ty: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 9 | 19",
    "7 | 9 | 14",
    "7 | 8 | 14",
    "7 | 7 | 14",
    "1 | 2 | 19",
    "1 | 2 | 14",
    "1 | 1 | 14",
    delimiter = '|'
  )
  fun cantRotateD2Cwb(x: Int, tx: Int, ty: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 2, 18),
          arrayOf(x, 19)
        ),
        2
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "7 | 4 | 19",
    "7 | 3 | 19",
    "5 | 6 | 19",
    "5 | 6 | 18",
    "5 | 6 | 17",
    "5 | 4 | 18",
    delimiter = '|'
  )
  fun cantRotateD3Cwb(x: Int, tx: Int, ty: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(lBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "8 | 9",
    "7 | 8",
    "6 | 7",
    "5 | 6",
    "4 | 5",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD0(x: Int, tx: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 2, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val tetris: Tetris by inject { parametersOf(lBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 5, 1, tx, 18)
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
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20)
        ),
        1
      )
    }
    val tetris: Tetris by inject { parametersOf(lBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 5, 2, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
    assertEquals(tetris.selectedBlock.direction, 2)
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
  fun rotateD2(x: Int, tx: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 2, 18),
          arrayOf(x, 19)
        ),
        2
      )
    }
    val tetris: Tetris by inject { parametersOf(lBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 5, 3, tx, 17)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
    assertEquals(tetris.selectedBlock.direction, 3)
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
  fun rotateD3(x: Int, tx: Int) {
    val lBlock: LBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x + 1, 18),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val tetris: Tetris by inject { parametersOf(lBlock) }
    tetris.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(25) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 5, 0, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, tetris.combineBlocks())
    assertEquals(tetris.selectedBlock.direction, 0)
  }
}