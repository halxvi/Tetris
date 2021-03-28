package com.example.tetris.model.field

import com.example.tetris.model.block.StraightBlock
import com.example.tetris.model.block.BlockInterface

class Field : FieldInterface {
  private var block: BlockInterface = StraightBlock(
    arrayOf(
      arrayOf(5, 1),
      arrayOf(6, 1),
      arrayOf(7, 1),
      arrayOf(8, 1)
    ),
    0,
    1
  )
  private var field: Array<Array<Int>> =
    Array(22) { Array<Int>(12) { 0 } }

  init {
    addWallToField()
  }

  private fun addWallToField() {
    for (n in 0..21) field[n][0] = 16
    for (n in 0..21) field[n][11] = 16
    for (i in 2..10) field[21][i] = 16
  }

  override fun addBlock() {
    block.coordinates.forEach {
      field[it[1]][it[0]] = block.type
    }
    addStraightBlock()
  }

  private fun addStraightBlock() {
    block = StraightBlock(
      arrayOf(
        arrayOf(5, 1),
        arrayOf(6, 1),
        arrayOf(7, 1),
        arrayOf(8, 1)
      ),
      0,
      1
    )
  }

  private fun addSquareBlock() {
    for (i in 5..6) {
      field[0][i] = 2
      field[1][i] = 2
    }
  }

  fun addZBlock() {
    for (i in 5..6) {
      field[0][i] = 3
      field[1][i + 1] = 3
    }
  }

  fun addZReverseBlock() {
    for (i in 5..6) {
      field[0][i + 1] = 4
      field[1][i] = 4
    }
  }

  fun addLBlock() {
    field[0][7] = 5
    for (i in 5..7) {
      field[1][i] = 5
    }
  }

  fun addLReverseBlock() {
    field[0][5] = 6
    for (i in 5..7) {
      field[1][i] = 6
    }
  }

  fun addTBlock() {
    field[0][6] = 7
    for (i in 5..7) {
      field[1][i] = 7
    }
  }

  override fun combineField(): Array<Array<Int>> {
    val combinedField = field
    block.coordinates.forEach {
      combinedField[it[1]][it[0]] = block.type
    }
    return combinedField
  }

  override fun moveBlock() {
    if (checkMoveBlock()) {
      for ((index, elem) in block.coordinates.withIndex()) {
        block.coordinates[index] = arrayOf(elem[0], elem[1]++)
      }
    }
  }

  private fun checkMoveBlock(): Boolean {
    block.coordinates.forEach {
      if (field[it[1]++][it[0]] != 0) {
        return false
      }
    }
    return true
  }

  override fun checkErasableBlock() {
    TODO("Not yet implemented")
  }

  override fun checkGameover() {
    TODO("Not yet implemented")
  }
}