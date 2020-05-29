package com.example.tetris

import java.security.SecureRandom;
import kotlin.concurrent.timer

class Tetris {
  private val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
  val fields: Array<Array<Int>> = Array(22) { Array<Int>(10) { 0 }; }
  private var score = 0;
  private var nextBlocks: MutableList<Int> = MutableList(5) {
    sr.nextInt(7) + 1
  }
  private var blockSelected: Boolean = false
  private val timer = timer(initialDelay = 1000, period = 100) {
    moveBlocks()
    checkErasableBlock()
  }
  var gameOver = false

  private fun addBlock() {
    checkOver()
    if (!blockSelected) {
      val nextBlock = this.nextBlocks.first()
      val setPlace = sr.nextInt(5) + 1
      when (nextBlock) {
        1 -> {
          //straight
          for (i in 0..3) {
            fields[1][setPlace + i] = 8
          }
        }
        2 -> {
          //square
          for (i in 0..1) {
            fields[0][setPlace + i] = 9
            fields[1][setPlace + i] = 9
          }
        }
        3 -> {
          //z
          for (i in 0..1) {
            fields[0][setPlace + i + 1] = 10
            fields[1][setPlace + i] = 10
          }
        }
        4 -> {
          //zReverse
          for (i in 0..1) {
            fields[0][setPlace + i] = 11
            fields[1][setPlace + i + 1] = 11
          }
        }
        5 -> {
          //L
          fields[0][setPlace] = 12
          for (i in 0..2) {
            fields[1][setPlace + i] = 12
          }
        }
        6 -> {
          //LReverse
          fields[0][setPlace + 2] = 13
          for (i in 0..2) {
            fields[1][setPlace + i] = 13
          }
        }
        7 -> {
          //t
          fields[0][setPlace + 1] = 14
          for (i in 0..2) {
            fields[1][setPlace + i] = 14
          }
        }
      }
      this.nextBlocks.removeAt(0)
      this.nextBlocks.add(sr.nextInt(7) + 1)
      blockSelected = true
    }
  }

  private fun checkOver() {
    for (i in 0..1) {
      for (n in 9 downTo 0) {
        when (fields[i][n]) {
          8, 9, 10, 11, 12, 13, 14 -> {
            timer.cancel()
            gameOver = true
          }
        }
      }
    }
  }

  private fun replaceBlocks() {
    if (blockSelected) {
      for (i in 21 downTo 2) {
        for (n in 9 downTo 0) {
          when (fields[i][n]) {
            8 -> {
              fields[i][n] = 1
            }
            9 -> {
              fields[i][n] = 2
            }
            10 -> {
              fields[i][n] = 3
            }
            11 -> {
              fields[i][n] = 4
            }
            12 -> {
              fields[i][n] = 5
            }
            13 -> {
              fields[i][n] = 6
            }
            14 -> {
              fields[i][n] = 7
            }
          }
        }
      }
      blockSelected = false
    }
  }

  private fun checkErasableBlock() {
    for (i in 21 downTo 2) {
      var flag: Boolean = false
      for (n in 9 downTo 0) {
        if (fields[i][n] == 0) {
          flag = false
          break
        }
        flag = true
      }
      if (flag) {
        for (j in 9 downTo 0) {
          fields[i][j] = 0
        }
        score += 1000
      }
    }
  }

  private fun checkAdjacentBlocks(): Boolean {
    for (i in 21 downTo 0) {
      for (n in 9 downTo 0) {
        if (i == 21) {
          when (fields[i][n]) {
            8, 9, 10, 11, 12, 13, 14 -> {
              return true
            }
          }
        } else {
          val currentBlock = fields[i][n]
          val targetBlock = fields[i + 1][n]
          when (currentBlock) {
            8, 9, 10, 11, 12, 13, 14 -> {
              when (targetBlock) {
                1, 2, 3, 4, 5, 6, 7 -> {
                  return true
                }
              }
            }
          }
        }
      }
    }
    return false
  }

  private fun moveBlocks() {
    if (checkAdjacentBlocks()) {
      replaceBlocks()
      addBlock()
    } else {
      for (i in 20 downTo 0) {
        for (n in 9 downTo 0) {
          val currentBlock = fields[i][n]
          val targetBlock = fields[i + 1][n]
          if (targetBlock == 0) {
            when (currentBlock) {
              8, 9, 10, 11, 12, 13, 14 -> {
                fields[i + 1][n] = currentBlock
                fields[i][n] = 0
              }
            }
          }
        }
      }
    }
  }


  fun getNextBlocks(): MutableList<Int> {
    return nextBlocks
  }

  fun getScore(): Int {
    return score
  }

  init {
    addBlock()
  }
}
