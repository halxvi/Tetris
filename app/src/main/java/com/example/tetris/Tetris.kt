package com.example.tetris

import java.security.SecureRandom;
import kotlin.concurrent.timer

class Tetris {
  private val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
  val fields: Array<Array<Int>> = Array(22) { Array<Int>(10) { 0 }; }
  var score = 0;
  private var nextBlocks: MutableList<Int> = MutableList(10) {
    sr.nextInt(7) + 1
  }

  private fun addBlock() {
    val nextBlock = this.nextBlocks.first()
    val setPlace = sr.nextInt(5) + 1
    when (nextBlock) {
      1 -> {
        //straight
        for (i in 0..3) {
          fields[1][setPlace + i] = nextBlock
        }
      }
      2 -> {
        //square
        for (i in 0..1) {
          fields[0][setPlace + i] = nextBlock
          fields[1][setPlace + i] = nextBlock
        }
      }
      3 -> {
        //z
        for (i in 0..1) {
          fields[0][setPlace + i + 2] = nextBlock
          fields[1][setPlace + i] = nextBlock
        }
      }
      4 -> {
        //zReverse
        for (i in 0..1) {
          fields[0][setPlace + i] = nextBlock
          fields[1][setPlace + i + 2] = nextBlock
        }
      }
      5 -> {
        //L
        fields[0][setPlace] = nextBlock
        for (i in 0..2) {
          fields[1][setPlace + i] = nextBlock
        }
      }
      6 -> {
        //LReverse
        fields[0][setPlace + 2] = nextBlock
        for (i in 0..2) {
          fields[1][setPlace + i] = nextBlock
        }
      }
      7 -> {
        //t
        fields[0][setPlace + 1] = nextBlock
        for (i in 0..2) {
          fields[1][setPlace + i] = nextBlock
        }
      }
    }
    this.nextBlocks.removeAt(0)
    this.nextBlocks.add(sr.nextInt(7) + 1)
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

  private fun moveBlocks() {
    for (i in 20 downTo 0) {
      for (n in 9 downTo 0) {
        val currentBlock = fields[i][n]
        val targetBlock = fields[i + 1][n]
        if (targetBlock == 0) {
          fields[i + 1][n] = currentBlock
          fields[i][n] = 0
        }
      }
    }
  }

  init {
    timer(initialDelay = 2000, period = 1000) {
      checkErasableBlock()
      moveBlocks()
    }
    timer(initialDelay = 0, period = 3000) {
      addBlock()
    }
  }
}
