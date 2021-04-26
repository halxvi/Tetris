package com.example.tetris.field

import com.example.tetris.block.*

class Field(
  var selectedBlock: BlockInterface = StraightBlock(),
  val blocks: Array<Array<Int>> =
    Array(22) { Array<Int>(12) { 0 } },
  private val nextBlocks: MutableList<Int> = mutableListOf(0, 0, 0),
  var heldBlock: Int = 0
) {
  init {
    for (n in 0..21) {
      blocks[n][0] = -1
      blocks[n][11] = -1
    }
    for (i in 2..10) blocks[21][i] = -1
  }

  fun addBlock() {
    selectedBlock.coordinates.forEach {
      blocks[it[1]][it[0]] = selectedBlock.type
    }
    when (nextBlocks.removeAt(0)) {
      1 -> selectedBlock = StraightBlock()
      2 -> selectedBlock = SquareBlock()
      3 -> selectedBlock = ZBlock()
      4 -> selectedBlock = ZReverseBlock()
      5 -> selectedBlock = LBlock()
      6 -> selectedBlock = LReverseBlock()
      7 -> selectedBlock = TBlock()
    }
    nextBlocks.add(1)
  }

  fun combineBlocks(): Array<Array<Int>> {
    val combinedField = blocks
    selectedBlock.coordinates.forEach {
      combinedField[it[1]][it[0]] = selectedBlock.type
    }
    return combinedField
  }

  fun moveBlock() {
    if (checkMoveBlock()) {
      for ((index, elem) in selectedBlock.coordinates.withIndex()) {
        selectedBlock.coordinates[index] = arrayOf(elem[0], elem[1]++)
      }
    }
  }

  private fun checkMoveBlock(): Boolean {
    selectedBlock.coordinates.forEach {
      if (blocks[it[1]++][it[0]] != 0) {
        return false
      }
    }
    return true
  }

  fun checkErasableBlock() {
  }

  fun checkGameover() {
  }
}