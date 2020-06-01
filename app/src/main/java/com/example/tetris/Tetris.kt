package com.example.tetris

import java.security.SecureRandom
import kotlin.concurrent.timer

class Tetris {
  private val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
  private var fields: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
  private var score = 0
  private var gameOver = false
  private var gameSpeed: Double = 500.0
  private var nextBlocks: MutableList<Int> = MutableList(14) {
    sr.nextInt(7) + 1
  }
  private var blockSelected: Boolean = false
  private var timer = timer(initialDelay = 1000, period = gameSpeed.toLong()) {
    moveBlocks()
    checkErasableBlock()
  }

  init {
    setBottom()
    addBlock()
  }

  fun init() {
    gameOver = false
    gameSpeed = 500.0
    blockSelected = false
    fields = Array(23) { Array<Int>(10) { 0 } }
    score = 0
    nextBlocks = MutableList(5) {
      sr.nextInt(7) + 1
    }
    timer = timer(initialDelay = 1000, period = gameSpeed.toLong()) {
      moveBlocks()
      checkErasableBlock()
    }
    setBottom()
    addBlock()
  }

  private fun setBottom() {
    for (i in 0..9) {
      fields[22][i] = 16
    }
  }

  private fun addBlock() {
    checkGameOver()
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

  private fun checkGameOver() {
    for (i in 0..1) {
      for (n in 0..9) {
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
            15 -> {
              fields[i][n] = 0
            }
          }
        }
      }
      blockSelected = false
    }
  }

  private fun checkErasableBlock() {
    for (i in 21 downTo 2) {
      var flag = false
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
        score += 100
      }
    }
  }

  private fun checkAdjacentBlocks(): Boolean {
    for (i in 21 downTo 0) {
      for (n in 9 downTo 0) {
        val currentBlock = fields[i][n]
        val targetBlock = fields[i + 1][n]
        when (currentBlock) {
          8, 9, 10, 11, 12, 13, 14 -> {
            when (targetBlock) {
              1, 2, 3, 4, 5, 6, 7, 16 -> {
                return true
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
    } else if (blockSelected) {
      for (i in 20 downTo 0) {
        for (n in 9 downTo 0) {
          val currentBlock = fields[i][n]
          val targetBlock = fields[i + 1][n]
          when (currentBlock) {
            8, 9, 10, 11, 12, 13, 14 -> {
              if (targetBlock == 0 || targetBlock == 15) {
                fields[i + 1][n] = currentBlock
                fields[i][n] = 0
              }
            }
          }
        }
      }
    }
  }

  fun flickBlock(option: String) {
    var flag = false
    when (option) {
      "right" -> {
        for (j in 0..21) {
          when (fields[j][9]) {
            8, 9, 10, 11, 12, 13, 14 -> {
              flag = true
            }
          }
        }
        loop@ for (n in 8 downTo 0) {
          for (i in 0..21) {
            if (flag) {
              break@loop
            }
            val currentBlock = fields[i][n]
            val targetBlock = fields[i][n + 1]
            when (currentBlock) {
              8, 9, 10, 11, 12, 13, 14 -> {
                if (targetBlock == 0 || !flag) {
                  fields[i][n + 1] = currentBlock
                  fields[i][n] = 0
                }
              }
            }
          }
        }
      }
      "left" -> {
        for (j in 0..21) {
          when (fields[j][0]) {
            8, 9, 10, 11, 12, 13, 14 -> {
              flag = true
            }
          }
        }
        loop@ for (n in 1..9) {
          for (i in 0..21) {
            if (flag) {
              break@loop
            }
            val currentBlock = fields[i][n]
            val targetBlock = fields[i][n - 1]
            when (currentBlock) {
              8, 9, 10, 11, 12, 13, 14 -> {
                if (targetBlock == 0 || !flag) {
                  fields[i][n - 1] = currentBlock
                  fields[i][n] = 0
                }
              }
            }
          }
        }
      }
    }
  }

  fun onSpeedUp() {
    val speed = 10
    for (i in 0..speed) {
      moveBlocks()
    }
  }

  private fun makeShadowBlock(copyFields: Array<Array<Int>>) {
    loop@ while (true) {
      for (i in 21 downTo 0) {
        for (n in 9 downTo 0) {
          val currentBlock = copyFields[i][n]
          val targetBlock = copyFields[i + 1][n]
          when (currentBlock) {
            8, 9, 10, 11, 12, 13, 14 -> {
              when (targetBlock) {
                1, 2, 3, 4, 5, 6, 7, 16 -> {
                  break@loop
                }
              }
            }
          }
        }
      }
      for (j in 20 downTo 0) {
        for (p in 9 downTo 0) {
          val currentBlock = copyFields[j][p]
          val targetBlock = copyFields[j + 1][p]
          if (targetBlock == 0 || targetBlock == 15) {
            when (currentBlock) {
              8, 9, 10, 11, 12, 13, 14 -> {
                copyFields[j + 1][p] = currentBlock
                copyFields[j][p] = 0
              }
            }
          }
        }
      }
    }
    for ((firstIndex, firstArray) in copyFields.withIndex()) {
      for ((lastIndex, block_num) in firstArray.withIndex()) {
        when (block_num) {
          8, 9, 10, 11, 12, 13, 14 -> {
            if (fields[firstIndex][lastIndex] == 0) {
              fields[firstIndex][lastIndex] = 15
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

  fun getGameOver(): Boolean {
    return gameOver
  }

  fun getGameSpeed(): Double {
    return gameSpeed / 10
  }

  fun getField(): Array<Array<Int>> {
    return fields
  }
}
