package com.example.tetris.model

import com.example.tetris.repository.TetrisModelRepository
import java.security.SecureRandom

class TetrisModel(repo: TetrisModelRepository) {
  private val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
  var fields: Array<Array<Int>> = repo.fields
  private var score: Int = repo.score
  private var gameover: Boolean = repo.gameover
  private var gameSpeed: Double = 500.0
  var nextBlocks: MutableList<Int> = repo.nextBlocks
  var isBlockSelected: Boolean = false
  private var blockDirection: Int = 0
  private var blockCategory: Int = 0

  init {
    for (i in 0..9) fields[22][i] = 16
  }

  fun addBlock() {
    if (isBlockSelected) {
      return
    }
    when (nextBlocks.first()) {
      1 -> {
        //straight
        for (i in 0..3) {
          fields[1][i] = 8
        }
        blockCategory = 8
      }
      2 -> {
        //square
        for (i in 0..1) {
          fields[0][i] = 9
          fields[1][i] = 9
        }
        blockCategory = 9
      }
      3 -> {
        //zReverse
        for (i in 0..1) {
          fields[0][i + 1] = 10
          fields[1][i] = 10
        }
        blockCategory = 10
      }
      4 -> {
        //z
        for (i in 0..1) {
          fields[0][i] = 11
          fields[1][i + 1] = 11
        }
        blockCategory = 11
      }
      5 -> {
        //L
        fields[0][0] = 12
        for (i in 0..2) {
          fields[1][i] = 12
        }
        blockCategory = 12
      }
      6 -> {
        //LReverse
        fields[0][2] = 13
        for (i in 0..2) {
          fields[1][i] = 13
        }
        blockCategory = 13
      }
      7 -> {
        //t
        fields[0][1] = 14
        for (i in 0..2) {
          fields[1][i] = 14
        }
        blockCategory = 14
      }
    }
    isBlockSelected = true
  }

  private fun checkGameOver() {
    for (i in 0..1) {
      for (n in 0..9) {
        when (fields[i][n]) {
          8, 9, 10, 11, 12, 13, 14 -> {
            gameover = true
          }
        }
      }
    }
  }

  private fun replaceBlocks() {
    if (!isBlockSelected) {
      return
    }
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
    blockDirection = 0
    isBlockSelected = false
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

  fun moveBlock() {
    if (!isBlockSelected) {
      return
    }
    if (checkAdjacentBlocks()) {
      replaceBlocks()
      addBlock()
      return
    }
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

  fun rotateBlock() {
    // 0 is neutral
    // 1 is once rotated by 90 deg
    // 2 is twice rotated by 90 deg
    // 3 is three times rotated by 90 deg
    var blockCount = 0
    loop@ for (i in 21 downTo 0) {
      for (n in 9 downTo 0) {
        when (blockCategory) {
          8 -> {
            when (n) {
              2, 3, 4, 5, 6, 7, 8 -> {
                when (blockDirection) {
                  0 -> {
                    if (blockCount == 1 && fields[i][n] == blockCategory) {
                      fields[i][n + 1] = 0
                      fields[i][n - 1] = 0
                      fields[i][n - 2] = 0
                      fields[i + 1][n] = blockCategory
                      fields[i - 1][n] = blockCategory
                      fields[i - 2][n] = blockCategory
                      blockDirection = 1
                      break@loop
                    } else if (fields[i][n] == blockCategory) {
                      blockCount += 1
                    }
                  }
                  1 -> {
                    if (blockCount == 1 && fields[i][n] == blockCategory) {
                      fields[i][n + 1] = blockCategory
                      fields[i][n - 1] = blockCategory
                      fields[i][n - 2] = blockCategory
                      fields[i + 1][n] = 0
                      fields[i - 1][n] = 0
                      fields[i - 2][n] = 0
                      blockDirection = 0
                      break@loop
                    } else if (fields[i][n] == blockCategory) {
                      blockCount += 1
                    }
                  }
                }
              }
            }
          }
          10 -> {
            if (blockCount == 0 && fields[i][n] == blockCategory) {
              when (n) {
                1, 2, 3, 4, 5, 6, 7, 8 -> {
                  when (blockDirection) {
                    0 -> {
                      fields[i][n - 1] = 0
                      fields[i - 1][n] = 0
                      fields[i - 1][n + 1] = 0
                      fields[i - 1][n] = blockCategory
                      fields[i][n + 1] = blockCategory
                      fields[i + 1][n + 1] = blockCategory
                      blockDirection = 1
                      break@loop
                    }
                    1 -> {
                      fields[i][n - 1] = 0
                      fields[i - 1][n] = 0
                      fields[i - 1][n + 1] = 0
                      fields[i - 1][n] = blockCategory
                      fields[i][n + 1] = blockCategory
                      fields[i + 1][n + 1] = blockCategory
                      blockDirection = 0
                      break@loop
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  fun getGameSpeed(): Double {
    return gameSpeed / 10
  }

  fun getField(): Array<Array<Int>> {
    return fields
  }

  fun onSpeedUp() {
    val speed = 10
    for (i in 0..speed) {
      moveBlock()
    }
  }

}
