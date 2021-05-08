package com.example.tetris.model

import com.example.tetris.block.*
import kotlin.random.Random

class Field(
  var selectedBlock: BlockInterface = InitBlock(),
  val blocks: Array<Array<Int>> =
    Array(24) { Array<Int>(12) { 0 } },
  val nextBlocks: MutableList<Int> = mutableListOf(0, 0, 0),
  private val random: Random = Random
) {
  var score: Int = 0

  init {
    addWallToBlocks()
    if (nextBlocks.elementAt(0) == 0) initNextBlocks()
  }

  private fun addWallToBlocks() {
    for (n in 0..23) {
      blocks[n][0] = -1
      blocks[n][11] = -1
    }
    for (i in 2..10) blocks[23][i] = -1
  }

  private fun initNextBlocks() {
    for (index in nextBlocks.indices) {
      nextBlocks[index] = random.nextInt(1, 7)
    }
  }

  fun addBlock() {
    blocks.apply { combineBlocks() }
    selectedBlock = when (nextBlocks.removeAt(0)) {
      1 -> StraightBlock()
      2 -> SquareBlock()
      3 -> ZBlock()
      4 -> ZReverseBlock()
      5 -> LBlock()
      6 -> LReverseBlock()
      7 -> TBlock()
      else -> SquareBlock()
    }
    nextBlocks.add(random.nextInt(1, 7))
  }

  fun combineBlocks(): Array<Array<Int>> {
    val combinedBlocks = blocks
    selectedBlock.coordinates.forEach {
      combinedBlocks[it[1]][it[0]] = selectedBlock.type
    }
    return combinedBlocks
  }

  fun moveBlock() {
    if (!canMoveBlock()) return
    for ((index, elem) in selectedBlock.coordinates.withIndex()) {
      selectedBlock.coordinates[index] = arrayOf(elem[0], elem[1] + 1)
    }
  }

  fun canMoveBlock(): Boolean {
    selectedBlock.coordinates.forEach {
      if (blocks[it[1] + 1][it[0]] != 0) return false
    }
    return true
  }

  fun flickToRight() {
    if (!canFlickToRight()) return
    for ((index, elem) in selectedBlock.coordinates.withIndex()) {
      selectedBlock.coordinates[index] = arrayOf(elem[0] + 1, elem[1])
    }
  }

  fun canFlickToRight(): Boolean {
    selectedBlock.coordinates.forEach {
      if (blocks[it[1]][it[0] + 1] != 0) return false
    }
    return true
  }

  fun flickToLeft() {
    if (!canFlickToLeft()) return
    for ((index, elem) in selectedBlock.coordinates.withIndex()) {
      selectedBlock.coordinates[index] = arrayOf(elem[0] - 1, elem[1])
    }
  }

  fun canFlickToLeft(): Boolean {
    selectedBlock.coordinates.forEach {
      if (blocks[it[1]][it[0] - 1] != 0) return false
    }
    return true
  }

  fun rotate() {
    selectedBlock.rotate(blocks)
  }

  fun eraseBlocks() {
    val y = findErasableBlocks()
    if (y == -1) return
    for (x in 1..10) blocks[y][x] = 0
    for (m in (y - 1)..0) {
      for (n in 10..1) {
        blocks[m + 1][n] = blocks[m][n]
      }
    }
    eraseBlocks()
    score += 10
  }

  fun findErasableBlocks(): Int {
    for ((i, a) in blocks.withIndex()) {
      if (a[1] != 0) {
        foundCrack@ for (n in 2..11) {
          if (a[n] == 0) break@foundCrack
          if (a[n] == -1) return i
        }
      }
    }
    return -1
  }

  fun isGameover(): Boolean {
    for (n in 1..10) {
      if (blocks[4][n] != 0 && blocks[3][n] != 0) {
        return true
      }
    }
    return false
  }
}