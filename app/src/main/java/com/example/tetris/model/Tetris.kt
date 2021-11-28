package com.example.tetris.model

import com.example.tetris.block.*
import kotlin.random.Random

class Tetris(
  var selectedBlock: BlockInterface = InitBlock(),
  override var blocks: Array<Array<Int>> =
    Array(25) { Array(12) { 0 } },
  override var nextBlocks: MutableList<Int> = mutableListOf(0, 0, 0),
  private var random: Random = Random,
  private var blockPool: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0),
  override var score: Int = 0,
  override var level: Int = 0
) : ITetris {
  init {
    addWallToBlocks()
    if (nextBlocks.elementAt(0) == 0) {
      fillBlockPool()
      shuffleBlockPool()
      initNextBlocks()
    }
  }

  private fun addWallToBlocks() {
    for (n in 0..24) {
      blocks[n][0] = -1
      blocks[n][11] = -1
    }
    for (i in 1..10) blocks[24][i] = -1
  }

  private fun fillBlockPool() {
    blockPool = mutableListOf(1, 2, 3, 4, 5, 6, 7)
  }

  private fun shuffleBlockPool() {
    for (i in 6 downTo 1) {
      val r = getRandomInt(i)
      val temp = blockPool[r]
      blockPool[r] = blockPool[i]
      blockPool[i] = temp
    }
  }

  private fun initNextBlocks() {
    for (index in nextBlocks.indices) {
      nextBlocks[index] = blockPool.removeAt(0)
    }
  }

  private fun getRandomInt(limit: Int): Int {
    return random.nextInt(0, limit)
  }

  private fun removeFirstNextBlock() {
    nextBlocks.removeAt(0)
  }

  private fun deepCopy(array: Array<Array<Int>>): Array<Array<Int>> {
    val blocks: Array<Array<Int>> =
      Array(array.size) { Array(array[0].size) { 0 } }
    repeat(array.size) {
      array[it].forEachIndexed { index, ints -> blocks[it][index] = ints }
    }
    return blocks
  }

  private fun calScore(): Int {
    return score + 10
  }

  private fun calLevel(): Int {
    if (score % 100 == 0) {
      return level++
    }
    return level
  }

  private fun addNextBlock() {
    if (blockPool.isEmpty()) {
      fillBlockPool()
      shuffleBlockPool()
    }
    nextBlocks.add(blockPool.removeAt(0))
  }

  fun addBlock() {
    blocks = combineBlocks()
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

  fun combineBlocks(): Array<Array<Int>> {
    val copiedBlocks = deepCopy(blocks)

    selectedBlock.coordinates.forEach {
      copiedBlocks[it[1]][it[0]] = selectedBlock.type
    }

    return copiedBlocks
  }

  fun combineShadowBlock(argBlocks: Array<Array<Int>>): Array<Array<Int>> {
    val copiedBlocks = deepCopy(argBlocks)
    val shadowBlockCoordinates = deepCopy(selectedBlock.coordinates)

    while (canMoveBlock(shadowBlockCoordinates)) {
      for ((index, elem) in shadowBlockCoordinates.withIndex()) {
        shadowBlockCoordinates[index] = arrayOf(elem[0], elem[1] + 1)
      }
    }

    shadowBlockCoordinates.forEach {
      if (copiedBlocks[it[1]][it[0]] == 0) copiedBlocks[it[1]][it[0]] = 8
    }

    return copiedBlocks
  }

  override fun combineAllBlocks(): Array<Array<Int>> {
    return combineShadowBlock(combineBlocks())
  }

  override fun moveBlock() {
    if (!canMoveBlock(selectedBlock.coordinates)) return

    val copyBlockCoordinates = deepCopy(selectedBlock.coordinates)

    for ((index, elem) in copyBlockCoordinates.withIndex()) {
      selectedBlock.coordinates[index] = arrayOf(elem[0], elem[1] + 1)
    }
  }

  override fun canMoveBlock(coordinates: Array<Array<Int>>): Boolean {
    coordinates.forEach {
      if (
        blocks[it[1] + 1][it[0]] == -1 ||
        blocks[it[1] + 1][it[0]] == 1 ||
        blocks[it[1] + 1][it[0]] == 2 ||
        blocks[it[1] + 1][it[0]] == 3 ||
        blocks[it[1] + 1][it[0]] == 4 ||
        blocks[it[1] + 1][it[0]] == 5 ||
        blocks[it[1] + 1][it[0]] == 6 ||
        blocks[it[1] + 1][it[0]] == 7
      ) return false
    }
    return true
  }

  override fun flickToRight() {
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

  override fun flickToLeft() {
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

  override fun rotate() {
    selectedBlock.rotate(blocks)
  }

  override fun eraseBlocks() {
    val y = findErasableBlocksIndex()

    if (y == -1 || y == 0) return

    for (x in 1..10) blocks[y][x] = 0

    for (m in (y - 1) downTo 0) {
      for (n in 10 downTo 1) {
        blocks[m + 1][n] = blocks[m][n]
      }
    }

    eraseBlocks()

    score = calScore()
    level = calLevel()
  }

  fun findErasableBlocksIndex(): Int {
    for ((index, array) in blocks.withIndex()) {
      if (
        array[1] == 1 ||
        array[1] == 2 ||
        array[1] == 3 ||
        array[1] == 4 ||
        array[1] == 5 ||
        array[1] == 6 ||
        array[1] == 7
      ) {
        cantErase@ for (n in 2..11) {
          if (array[n] == 0) break@cantErase
          if (array[n] == -1) return index
        }
      }
    }
    return -1
  }

  override fun isGameover(): Boolean {
    repeat(10) {
      selectedBlock.coordinates.forEach {
        if (!canMoveBlock(selectedBlock.coordinates) && it[1] <= 3) {
          return true
        }
      }
    }
    return false
  }

  override fun startGame() {
    addBlock()
  }

  override fun fallDownBlock() {
    while (canMoveBlock(selectedBlock.coordinates)) moveBlock()
  }

  override fun restart() {
    selectedBlock = InitBlock()
    blocks = Array(25) { Array(12) { 0 } }
    nextBlocks = mutableListOf(0, 0, 0)
    random = Random
    blockPool = mutableListOf(0, 0, 0, 0, 0, 0, 0)
    score = 0
    addWallToBlocks()
    fillBlockPool()
    shuffleBlockPool()
    initNextBlocks()
  }
}