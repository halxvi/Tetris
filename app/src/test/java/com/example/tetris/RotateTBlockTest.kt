package com.example.tetris

import com.example.tetris.Utilities.Companion.addWallToBlocks
import com.example.tetris.Utilities.Companion.insertBlock
import com.example.tetris.block.BlockInterface
import com.example.tetris.block.TBlock
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

class RotateTBlockTest : KoinTest {
  @JvmField
  @RegisterExtension
  val koinTestExtension = KoinTestExtension.create {
    modules(
      module {
        single { (
                   coordinates: Array<Array<Int>>,
                   direction: Int
                 ) ->
          TBlock(
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
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x, 20),
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
    assertTrue(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8", "9"
  )
  fun canRotateD1(x: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
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
    assertTrue(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8"
  )
  fun canRotateD2(x: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 2, 19),
          arrayOf(x + 1, 20)
        ),
        2
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "1", "2", "3", "4", "5", "6", "7", "8"
  )
  fun canRotateD3(x: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      addWallToBlocks(expectedBlocks)
    }
    assertTrue(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "5 | 7 | 18",
    "5 | 6 | 17",
    "4 | 1 | 18",
    "4 | 1 | 17",
    delimiter = '|'
  )
  fun cantRotateD0Cwb(x: Int, tx: Int, ty: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "6 | 7 | 19",
    "6 | 7 | 17",
    "6 | 2 | 19",
    "6 | 2 | 18",
    "1 | 2 | 19",
    "1 | 3 | 18",
    "1 | 2 | 17",
    delimiter = '|'
  )
  fun cantRotateD1Cwb(x: Int, tx: Int, ty: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "6 | 8 | 16",
    "6 | 8 | 11",
    "6 | 7 | 11",
    "6 | 6 | 11",
    "4 | 6 | 16",
    "4 | 4 | 16",
    "4 | 5 | 11",
    "4 | 4 | 11",
    delimiter = '|'
  )
  fun cantRotateD2Cwb(x: Int, tx: Int, ty: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 15),
          arrayOf(x + 1, 15),
          arrayOf(x + 2, 15),
          arrayOf(x + 1, 16)
        ),
        2
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 1, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(tBlock.canRotate(expectedBlocks))
  }

  @ParameterizedTest
  @CsvSource(
    "6 | 3 | 19",
    "6 | 2 | 19",
    "4 | 6 | 19",
    "4 | 6 | 18",
    "4 | 6 | 17",
    "4 | 1 | 19",
    delimiter = '|'
  )
  fun cantRotateD3Cwb(x: Int, tx: Int, ty: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 1, 0, tx, ty)
      addWallToBlocks(expectedBlocks)
    }
    assertFalse(tBlock.canRotate(expectedBlocks))
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
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 19),
          arrayOf(x, 20),
          arrayOf(x + 1, 20),
          arrayOf(x + 2, 20)
        ),
        0
      )
    }
    val field: Field by inject { parametersOf(tBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 7, 1, tx, 18)
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
    "5 | 5",
    "4 | 4",
    "3 | 3",
    "2 | 2",
    "1 | 1",
    delimiter = '|'
  )
  fun rotateD1(x: Int, tx: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x, 20)
        ),
        1
      )
    }
    val field: Field by inject { parametersOf(tBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 7, 2, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 2)
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
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x, 15),
          arrayOf(x + 1, 15),
          arrayOf(x + 2, 15),
          arrayOf(x + 1, 16)
        ),
        2
      )
    }
    val field: Field by inject { parametersOf(tBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 7, 3, tx, 14)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 3)
  }

  @ParameterizedTest
  @CsvSource(
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
  fun rotateD3(x: Int, tx: Int) {
    val tBlock: TBlock by inject {
      parametersOf(
        arrayOf(
          arrayOf(x + 1, 18),
          arrayOf(x, 19),
          arrayOf(x + 1, 19),
          arrayOf(x + 1, 20)
        ),
        3
      )
    }
    val field: Field by inject { parametersOf(tBlock) }
    field.rotate()
    val expectedBlocks: Array<Array<Int>> = Array(24) { Array(12) { 0 } }
    expectedBlocks.apply {
      insertBlock(expectedBlocks, 7, 0, tx, 19)
      addWallToBlocks(expectedBlocks)
    }
    assertArrayEquals(expectedBlocks, field.combineBlocks())
    assertEquals(field.selectedBlock.direction, 0)
  }
}