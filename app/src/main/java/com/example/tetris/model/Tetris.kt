package com.example.tetris.model

class Tetris(
  private val field: Field
) {
  var score: Int = 0
  var gameover: Boolean = false
  private var gameSpeed: Double = 500.0

  fun addBlock() {
    field.addBlock()
  }

  fun moveBlock() {
    field.moveBlock()
  }

  fun getBlocks(): Array<Array<Int>> {
    return field.combineBlocks()
  }

//  private fun checkGameOver() {
//    for (i in 0..1) {
//      for (n in 0..9) {
//        when (fields[i][n]) {
//          8, 9, 10, 11, 12, 13, 14 -> {
//            gameover = true
//          }
//        }
//      }
//    }
//  }
//
//  private fun checkErasableBlock() {
//    for (i in 21 downTo 2) {
//      var flag = false
//      for (n in 9 downTo 0) {
//        if (fields[i][n] == 0) {
//          flag = false
//          break
//        }
//        flag = true
//      }
//      if (flag) {
//        for (j in 9 downTo 0) {
//          fields[i][j] = 0
//        }
//        score += 100
//      }
//    }
//  }
//
//  private fun checkAdjacentBlocks(): Boolean {
//    for (i in 21 downTo 0) {
//      for (n in 9 downTo 0) {
//        val currentBlock = fields[i][n]
//        val targetBlock = fields[i + 1][n]
//        when (currentBlock) {
//          8, 9, 10, 11, 12, 13, 14 -> {
//            when (targetBlock) {
//              1, 2, 3, 4, 5, 6, 7, 16 -> {
//                return true
//              }
//            }
//          }
//        }
//      }
//    }
//    return false
//  }
//
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
//  fun flickBlock(option: String) {
//    var flag = false
//    when (option) {
//      "right" -> {
//        for (j in 0..21) {
//          when (fields[j][9]) {
//            8, 9, 10, 11, 12, 13, 14 -> {
//              flag = true
//            }
//          }
//        }
//        loop@ for (n in 8 downTo 0) {
//          for (i in 0..21) {
//            if (flag) {
//              break@loop
//            }
//            val currentBlock = fields[i][n]
//            val targetBlock = fields[i][n + 1]
//            when (currentBlock) {
//              8, 9, 10, 11, 12, 13, 14 -> {
//                if (targetBlock == 0 || !flag) {
//                  fields[i][n + 1] = currentBlock
//                  fields[i][n] = 0
//                }
//              }
//            }
//          }
//        }
//      }
//      "left" -> {
//        for (j in 0..21) {
//          when (fields[j][0]) {
//            8, 9, 10, 11, 12, 13, 14 -> {
//              flag = true
//            }
//          }
//        }
//        loop@ for (n in 1..9) {
//          for (i in 0..21) {
//            if (flag) {
//              break@loop
//            }
//            val currentBlock = fields[i][n]
//            val targetBlock = fields[i][n - 1]
//            when (currentBlock) {
//              8, 9, 10, 11, 12, 13, 14 -> {
//                if (targetBlock == 0 || !flag) {
//                  fields[i][n - 1] = currentBlock
//                  fields[i][n] = 0
//                }
//              }
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