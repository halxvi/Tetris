package com.example.tetris

import com.example.tetris.block.*

class Utilities {
  companion object {
    fun addWallToBlocks(array: Array<Array<Int>>): Array<Array<Int>> {
      for (n in 0..23) {
        array[n][0] = -1
        array[n][11] = -1
      }
      for (i in 2..10) array[23][i] = -1
      return array
    }

    fun testBlock(blockType: Int): BlockInterface {
      return when (blockType) {
        1 -> StraightBlock()
        2 -> SquareBlock()
        3 -> ZBlock()
        4 -> ZReverseBlock()
        5 -> LBlock()
        6 -> LReverseBlock()
        7 -> TBlock()
        else -> SquareBlock()
      }
    }

    fun insertBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      direction: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (type) {
        1 -> return insertStraightBlock(
          expectedBlocks,
          type,
          direction,
          x,
          y
        )
        2 -> return insertSquareBlock(
          expectedBlocks,
          type,
          x,
          y
        )
        3 -> return insertZBlock(
          expectedBlocks,
          type,
          direction,
          x,
          y
        )
        4 -> {
          if (x > 7) return expectedBlocks
          for (i in 0..1) {
            expectedBlocks[y][x + i + 1] = 4
            expectedBlocks[y + 1][x + i] = 4
          }
        }
        5 -> {
          if (x > 7) return expectedBlocks
          expectedBlocks[y][x + 2] = 5
          for (i in 0..2) {
            expectedBlocks[y + 1][x + i] = 5
          }
        }
        6 -> {
          if (x > 7) return expectedBlocks
          expectedBlocks[y][x] = 6
          for (i in 0..2) {
            expectedBlocks[y + 1][x + i] = 6
          }
        }
        7 -> {
          if (x > 8) return expectedBlocks
          expectedBlocks[y][x + 1] = 7
          for (i in 0..2) {
            expectedBlocks[y + 1][x + i] = 7
          }
        }
      }
      return expectedBlocks
    }

    fun insertShadowBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      direction: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (type) {
        1 -> insertStraightBlock(
          expectedBlocks,
          8,
          direction,
          x,
          y
        )
        2 -> return insertSquareBlock(
          expectedBlocks,
          8,
          x,
          y
        )
        3 -> return insertZBlock(
          expectedBlocks,
          8,
          direction,
          x,
          y
        )
      }
      return expectedBlocks
    }

    private fun insertStraightBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      direction: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (direction) {
        0 -> {
          for (i in 0..3) {
            expectedBlocks[y][x + i] = type
          }
        }
        1 -> {
          for (i in 0..3) {
            expectedBlocks[y + i][x] = type
          }
        }
      }
      return expectedBlocks
    }

    private fun insertSquareBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      for (i in 0..1) {
        expectedBlocks[y - 1][x + i] = type
        expectedBlocks[y][x + i] = type
      }
      return expectedBlocks
    }

    private fun insertZBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      direction: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (direction) {
        0 -> {
          for (i in 0..1) {
            expectedBlocks[y - 1][x + i] = type
            expectedBlocks[y][x + 1 + i] = type
          }
        }
        1 -> {
          for (i in 0..1) {
            expectedBlocks[y - 1 - i][x + i] = type
            expectedBlocks[y - i][x + i] = type
          }
        }
      }
      return expectedBlocks
    }
  }
}