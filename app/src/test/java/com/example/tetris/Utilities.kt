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
        4 -> return insertZReverseBlock(
          expectedBlocks,
          type,
          direction,
          x,
          y
        )
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
        4 -> return insertZReverseBlock(
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
            expectedBlocks[y + 1][x + i] = type
          }
        }
        1 -> {
          for (i in 0..3) {
            expectedBlocks[y + i + 1][x] = type
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
      expectedBlocks[y][x] = type
      expectedBlocks[y][x + 1] = type
      expectedBlocks[y + 1][x] = type
      expectedBlocks[y + 1][x + 1] = type
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
          expectedBlocks[y][x] = type
          expectedBlocks[y][x + 1] = type
          expectedBlocks[y + 1][x + 1] = type
          expectedBlocks[y + 1][x + 2] = type
        }
        1 -> {
          expectedBlocks[y + 1][x] = type
          expectedBlocks[y + 2][x] = type
          expectedBlocks[y][x + 1] = type
          expectedBlocks[y + 1][x + 1] = type
        }
      }
      return expectedBlocks
    }

    private fun insertZReverseBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      direction: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (direction) {
        0 -> {
          expectedBlocks[y][x + 1] = 4
          expectedBlocks[y][x + 2] = 4
          expectedBlocks[y + 1][x] = 4
          expectedBlocks[y + 1][x + 1] = 4
        }
        1 -> {
          expectedBlocks[y][x] = type
          expectedBlocks[y - 1][x] = type
          expectedBlocks[y - 1][x + 1] = type
          expectedBlocks[y - 2][x + 1] = type
        }
      }
      return expectedBlocks
    }
  }
}