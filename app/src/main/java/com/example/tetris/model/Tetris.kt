package com.example.tetris.model

class Tetris(
  private val field: Field
) {
  var gameover: Boolean = false
  var gameSpeed: Double = 500.0
  var score: Int = 0

  fun addBlock() {
    field.addBlock()
  }

  fun moveBlock() {
    field.moveBlock()
  }

  fun updateScore() {
    score = field.score
  }

  fun combineBlocks(): Array<Array<Int>> {
    return field.combineBlocks()
  }

//  private fun makeShadowBlock(copyFields: Array<Array<Int>>) {
//    loop@ while (true) {
//      for (i in 21 downTo 0) {
//        for (n in 9 downTo 0) {
//          val currentBlock = copyFields[i][n]
//          val targetBlock = copyFields[i + 1][n]
//          when (currentBlock) {
//            8, 9, 10, 11, 12, 13, 14 -> {
//              when (targetBlock) {
//                1, 2, 3, 4, 5, 6, 7, 16 -> {
//                  break@loop
//                }
//              }
//            }
//          }
//        }
//      }
//      for (j in 20 downTo 0) {
//        for (p in 9 downTo 0) {
//          val currentBlock = copyFields[j][p]
//          val targetBlock = copyFields[j + 1][p]
//          if (targetBlock == 0 || targetBlock == 15) {
//            when (currentBlock) {
//              8, 9, 10, 11, 12, 13, 14 -> {
//                copyFields[j + 1][p] = currentBlock
//                copyFields[j][p] = 0
//              }
//            }
//          }
//        }
//      }
//    }
//    for ((firstIndex, firstArray) in copyFields.withIndex()) {
//      for ((lastIndex, block_num) in firstArray.withIndex()) {
//        when (block_num) {
//          8, 9, 10, 11, 12, 13, 14 -> {
//            if (fields[firstIndex][lastIndex] == 0) {
//              fields[firstIndex][lastIndex] = 15
//            }
//          }
//        }
//      }
//    }
//  }
//

  fun onSpeedUp() {
    val speed = 10
    for (i in 0..speed) {
    }
  }
}