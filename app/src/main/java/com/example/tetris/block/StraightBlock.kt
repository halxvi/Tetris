package com.example.tetris.block

class StraightBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(5, 3),
    arrayOf(6, 3),
    arrayOf(7, 3),
    arrayOf(8, 3)
  ),
  override var direction: Int = 0,
  override val type: Int = 1
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {
    if (!canRotate(blocks)) return
    when (direction) {
      0 -> rotateD0()
      1 -> rotateD1()
    }
    direction = if (direction == 0) 1 else 0
  }

  fun canRotate(blocks: Array<Array<Int>>): Boolean {
    return when (direction) {
      0 -> canRotateD0(blocks)
      1 -> canRotateD1(blocks)
      else -> false
    }
  }

  // d0 : direction 0

  private fun canRotateD0(blocks: Array<Array<Int>>): Boolean {
    if (getX(0) <= 5) {
      if (blocks[getY(1) - 1][getX(1) - 1] != 0) return false
      if (blocks[getY(2) - 2][getX(2) - 2] != 0) return false
      if (blocks[getY(3) - 3][getX(3) - 3] != 0) return false
    } else {
      if (blocks[getY(0) - 3][getX(0) + 3] != 0) return false
      if (blocks[getY(1) - 2][getX(1) + 2] != 0) return false
      if (blocks[getY(2) - 1][getX(2) + 1] != 0) return false
    }
    return true
  }

  private fun canRotateD1(blocks: Array<Array<Int>>): Boolean {
    if (getX(0) <= 5) {
      if (blocks[getY(0) + 3][getX(0) + 3] != 0) return false
      if (blocks[getY(1) + 2][getX(1) + 2] != 0) return false
      if (blocks[getY(2) + 1][getX(2) + 1] != 0) return false
    } else {
      if (blocks[getY(0) + 3][getX(0) - 3] != 0) return false
      if (blocks[getY(1) + 2][getX(1) - 2] != 0) return false
      if (blocks[getY(2) + 1][getX(2) - 1] != 0) return false
    }
    return true
  }

  private fun rotateD0() {
    val nextCoordinates: Array<Array<Int>> = arrayOf(
      arrayOf(0, 0),
      arrayOf(0, 0),
      arrayOf(0, 0),
      arrayOf(0, 0)
    )
    if (getX(0) <= 5) {
      for ((i) in coordinates.withIndex()) {
        nextCoordinates[3 - i] = arrayOf(getX(i) - i, getY(i) - i)
      }
    } else {
      for ((i) in coordinates.withIndex()) {
        nextCoordinates[i] = arrayOf(getX(i) + 3 - i, getY(i) - 3 + i)
      }
    }
    coordinates = nextCoordinates
  }

  private fun rotateD1() {
    val nextCoordinates: Array<Array<Int>> = arrayOf(
      arrayOf(0, 0),
      arrayOf(0, 0),
      arrayOf(0, 0),
      arrayOf(0, 0)
    )
    if (getX(0) <= 5) {
      for ((i) in coordinates.withIndex()) {
        nextCoordinates[3 - i] = arrayOf(getX(i) + 3 - i, getY(i) + 3 - i)
      }
    } else {
      for ((i) in coordinates.withIndex()) {
        nextCoordinates[i] = arrayOf(getX(i) - 3 + i, getY(i) + 3 - i)
      }
    }
    coordinates = nextCoordinates
  }

  private fun getX(i: Int): Int {
    return coordinates[i][0]
  }

  private fun getY(i: Int): Int {
    return coordinates[i][1]
  }
}