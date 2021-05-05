package com.example.tetris

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

    fun insertBlock(
      expectedBlocks: Array<Array<Int>>,
      type: Int,
      x: Int,
      y: Int
    ): Array<Array<Int>> {
      when (type) {
        1 -> {
          if (x > 7) return expectedBlocks
          for (i in 0..3) {
            expectedBlocks[y][x + i] = 1
          }
        }
        2 -> {
          if (x > 9) return expectedBlocks
          for (i in 0..1) {
            expectedBlocks[y][x + i] = 2
            expectedBlocks[y + 1][x + i] = 2
          }
        }
        3 -> {
          if (x > 7) return expectedBlocks
          for (i in 0..1) {
            expectedBlocks[y][x + i] = 3
            expectedBlocks[y + 1][x + i + 1] = 3
          }
        }
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
  }
}