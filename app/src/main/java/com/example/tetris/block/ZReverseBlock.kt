package com.example.tetris.block

class ZReverseBlock(
  override var coordinates: Array<Array<Int>> = arrayOf(
    arrayOf(6, 2),
    arrayOf(7, 2),
    arrayOf(5, 3),
    arrayOf(6, 3)
  ),
  override var direction: Int = 0,
  override val type: Int = 4
) : BlockInterface {
  override fun rotate(blocks: Array<Array<Int>>) {
    if (!canRotate(blocks)) return
    when (direction) {
      0 -> rotateD0()
      1 -> rotateD1()
    }
    direction = if (direction == 0) 1 else 0
  }

  override fun canRotate(blocks: Array<Array<Int>>): Boolean {
    return when (direction) {
      0 -> canRotateD0(blocks)
      1 -> canRotateD1(blocks)
      else -> false
    }
  }

  private fun canRotateD0(blocks: Array<Array<Int>>): Boolean {
    if (getX(0) <= 5) {
      if (blocks[getY(0) - 1][getX(0) - 1] != 0) return false
      if (blocks[getY(2) - 2][getX(2)] != 0) return false
      if (blocks[getY(3)][getX(3) + 1] != 0) return false
    } else {
      if (blocks[getY(0) - 1][getX(0)] != 0) return false
      if (blocks[getY(3)][getX(3) + 1] != 0) return false
    }
    return true
  }

  private fun canRotateD1(blocks: Array<Array<Int>>): Boolean {
    if (getX(0) <= 5) {
      if (blocks[getY(2)][getX(2) + 1] != 0) return false
      if (blocks[getY(3)][getX(3) - 1] != 0) return false
    } else {
      if (blocks[getY(1) + 1][getX(1)] != 0) return false
      if (blocks[getY(1) + 1][getX(1) - 1] != 0) return false
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
      nextCoordinates[0] = arrayOf(getX(0) - 1, getY(0) - 1)
      nextCoordinates[1] = arrayOf(getX(1) - 2, getY(1))
      nextCoordinates[2] = arrayOf(getX(2) + 1, getY(2) - 1)
      nextCoordinates[3] = arrayOf(getX(3), getY(3))
    } else {
      nextCoordinates[0] = arrayOf(getX(0), getY(0) - 1)
      nextCoordinates[1] = arrayOf(getX(1) - 1, getY(1))
      nextCoordinates[2] = arrayOf(getX(2) + 2, getY(2) - 1)
      nextCoordinates[3] = arrayOf(getX(3) + 1, getY(3))
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
      nextCoordinates[0] = arrayOf(getX(0) + 1, getY(0) + 1)
      nextCoordinates[1] = arrayOf(getX(1) + 2, getY(1))
      nextCoordinates[2] = arrayOf(getX(2) - 1, getY(2) + 1)
      nextCoordinates[3] = arrayOf(getX(3), getY(3))
    } else {
      nextCoordinates[0] = arrayOf(getX(0), getY(0) + 1)
      nextCoordinates[1] = arrayOf(getX(1) + 1, getY(1))
      nextCoordinates[2] = arrayOf(getX(2) - 2, getY(2) + 1)
      nextCoordinates[3] = arrayOf(getX(3) - 1, getY(3))
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