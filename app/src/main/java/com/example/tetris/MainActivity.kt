package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
  private val tetris = Tetris()
  private val handler = Handler()
  private var timer = timer(initialDelay = 1000, period = 100) {
    checkGameOver(tetris.gameOver)
    updateField(handler, tetris.fields)
    updateHeader(handler, tetris.getScore(), tetris.getNextBlocks())
  }
  var gameSpeed:Double = 1000.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    gameOverLayout.visibility = View.INVISIBLE

//    fun setTimer() {
//      timer.cancel()
//      timer.schedule() {
//        updateField(tetris.fields)
//        updateScore(tetris.score)
//      }
//    }
  }

  private fun checkGameOver(gameOverFlag: Boolean) {
    if (gameOverFlag) {
      timer.cancel()
      handler.post {
        gameOverLayout.visibility = View.VISIBLE
        gameOverScore.text = tetris.getScore().toString()
      }
    }
  }

  private fun updateField(handler: Handler, fields: Array<Array<Int>>) {
    handler.post {
      fields.forEachIndexed { firstIndex, array ->
        if (firstIndex < 19) {
          array.forEachIndexed { lastIndex, block_num ->
            val blockArrayIndex = (firstIndex) * 10 + (lastIndex + 1)
            val resourceId = resources.getIdentifier("block_$blockArrayIndex", "id", packageName)
            val blockId: ImageView = findViewById(resourceId)
            when (block_num) {
              1 -> {
                blockId.setImageResource(R.drawable.lightblue_block)
              }
              2 -> {
                blockId.setImageResource(R.drawable.yellow_block)
              }
              3 -> {
                blockId.setImageResource(R.drawable.red_block)
              }
              4 -> {
                blockId.setImageResource(R.drawable.green_block)
              }
              5 -> {
                blockId.setImageResource(R.drawable.blue_block)
              }
              6 -> {
                blockId.setImageResource(R.drawable.orange_block)
              }
              7 -> {
                blockId.setImageResource(R.drawable.purple_block)
              }
              8, 9, 10, 11, 12, 13, 14 -> {
                blockId.setImageResource(R.drawable.black_block)
              }
              15 -> {
                blockId.setImageResource(R.drawable.target_block_background)
              }
              else -> {
                blockId.setImageResource(R.drawable.block_background)
              }
            }
          }
        }
      }
    }
  }

  private fun updateHeader(handler: Handler, score: Int, nextBlocks: MutableList<Int>) {
    handler.post {
      when (nextBlocks[0]) {
        4 -> {
          nextBlockView.setImageResource(R.mipmap.green_block)
        }
        5 -> {
          nextBlockView.setImageResource(R.mipmap.blue_block)
        }
      }
      when (nextBlocks[1]) {
        4 -> {
          nextBlockView2.setImageResource(R.mipmap.green_block)
        }
        5 -> {
          nextBlockView2.setImageResource(R.mipmap.blue_block)
        }
      }
      when (nextBlocks[2]) {
        4 -> {
          nextBlockView3.setImageResource(R.mipmap.green_block)
        }
        5 -> {
          nextBlockView3.setImageResource(R.mipmap.blue_block)
        }
      }
      scoreView.text = "Your Score:$score"
    }
  }
}
