package com.example.tetris.model

import com.example.tetris.block.*
import kotlin.random.Random

class Tetris(
  var selectedBlock: BlockInterface = InitBlock(),
  val blocks: Array<Array<Int>> =
    Array(24) { Array(12) { 0 } },
  val nextBlocks: MutableList<Int> = mutableListOf(0, 0, 0),
  private val random: Random = Random,
  var score: Int = 0
) {
  private var sleepTime: Long = 500

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
    selectedBlock = when (nextBlocks[0]) {
      1 -> StraightBlock()
      2 -> SquareBlock()
      3 -> ZBlock()
      4 -> ZReverseBlock()
      5 -> LBlock()
      6 -> LReverseBlock()
      7 -> TBlock()
      else -> SquareBlock()
    }
    removeFirstNextBlock()
    addNextBlock()
  }

  private fun addNextBlock(){
    nextBlocks.add(random.nextInt(1, 7))
  }

  private fun removeFirstNextBlock(){
    nextBlocks.removeAt(0)
  }

  fun combineBlocks(): Array<Array<Int>> {
    val copiedBlocks = deepCopy(blocks)
    selectedBlock.coordinates.forEach {
      copiedBlocks[it[1]][it[0]] = selectedBlock.type
    }
    return copiedBlocks
  }

  fun combineShadowBlock(): Array<Array<Int>> {
    val copiedBlocks = deepCopy(blocks)
    val shadowBlockCoordinates = deepCopy(selectedBlock.coordinates)
    while (canMoveBlock()) {
      for ((index, elem) in shadowBlockCoordinates.withIndex()) {
        shadowBlockCoordinates[index] = arrayOf(elem[0], elem[1] + 1)
      }
    }
    shadowBlockCoordinates.forEach {
      copiedBlocks[it[1]][it[0]] = 8
    }
    return copiedBlocks
  }

  fun combineAllBlocks(): Array<Array<Int>> {
    return combineBlocks().apply { combineShadowBlock() }
  }

  fun moveBlock() {
    if (!canMoveBlock()) return
    val copyBlockCoordinates = deepCopy(selectedBlock.coordinates)
    for ((index, elem) in copyBlockCoordinates.withIndex()) {
      selectedBlock.coordinates[index] = arrayOf(elem[0], elem[1] + 1)
    }
  }

  fun canMoveBlock(): Boolean {
    selectedBlock.coordinates.forEach {
      if(
        blocks[it[1] + 1][it[0]] == -1||
        blocks[it[1] + 1][it[0]] == 1||
        blocks[it[1] + 1][it[0]] == 2||
        blocks[it[1] + 1][it[0]] == 3||
        blocks[it[1] + 1][it[0]] == 4||
        blocks[it[1] + 1][it[0]] == 5||
        blocks[it[1] + 1][it[0]] == 6||
        blocks[it[1] + 1][it[0]] == 7
      ) return false
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
      for (n in 10 downTo 1) {
        blocks[m + 1][n] = blocks[m][n]
      }
    }
    eraseBlocks()
    addScore()
  }

  private fun addScore(){
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
    repeat(10) {
      selectedBlock.coordinates.forEach {
        if (!canMoveBlock() && it[1] == 3) {
          return true
        }
      }
    }
    return false
  }

  fun startGame() {
    addBlock()
  }

  private fun deepCopy(array:Array<Array<Int>>): Array<Array<Int>> {
    val blocks: Array<Array<Int>> =
      Array(array.size) { Array(array[0].size) { 0 } }
    repeat(array.size){
      array[it].forEachIndexed { index, ints -> blocks[it][index] = ints }
    }
    return blocks
  }
}