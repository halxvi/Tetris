package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
  private lateinit var mDetector: GestureDetectorCompat
  private val tetris = Tetris()
  private val handler = Handler()
  private var timer = timer(period = tetris.getGameSpeed().toLong()) {
    checkGameOver(tetris.getGameOver())
    updateField(handler, tetris.getField())
    updateHeader(handler, tetris.getScore(), tetris.getNextBlocks())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mDetector = GestureDetectorCompat(this, MyGestureListener(this.tetris))
    restartButton.setOnClickListener {
      tetris.init()
      timer = timer(period = tetris.getGameSpeed().toLong()) {
        checkGameOver(tetris.getGameOver())
        updateField(handler, tetris.getField())
        updateHeader(handler, tetris.getScore(), tetris.getNextBlocks())
      }
      gameOverLayout.visibility = View.INVISIBLE
    }
    gameOverLayout.visibility = View.INVISIBLE
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    mDetector.onTouchEvent(event)
    return super.onTouchEvent(event)
  }

  private class MyGestureListener(private val tetris: Tetris) :
    GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
      e1: MotionEvent?,
      e2: MotionEvent?,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      val calculateX = e1!!.x - e2!!.x
      if (calculateX < 0) {
        tetris.flickBlock("right")
      } else {
        tetris.flickBlock("left")
      }
      return super.onFling(e1, e2, velocityX, velocityY)
    }

    override fun onLongPress(e: MotionEvent?) {
      tetris.onSpeedUp()
      super.onLongPress(e)
    }
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
        if (firstIndex in 2..21) {
          array.forEachIndexed { lastIndex, block_num ->
            val blockArrayIndex = (firstIndex - 2) * 10 + (lastIndex + 1)
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
      for (i in 1..3) {
        val resourceId = resources.getIdentifier("nextBlockView$i", "id", packageName)
        val nextBlockView: ImageView = findViewById(resourceId)
        when (nextBlocks[i - 1]) {
          1 -> {
            nextBlockView.setImageResource(R.drawable.lightblue_block)
          }
          2 -> {
            nextBlockView.setImageResource(R.drawable.yellow_block)
          }
          3 -> {
            nextBlockView.setImageResource(R.drawable.red_block)
          }
          4 -> {
            nextBlockView.setImageResource(R.drawable.green_block)
          }
          5 -> {
            nextBlockView.setImageResource(R.drawable.blue_block)
          }
          6 -> {
            nextBlockView.setImageResource(R.drawable.orange_block)
          }
          7 -> {
            nextBlockView.setImageResource(R.drawable.purple_block)
          }
        }
      }
      scoreView.text = "Your Score:$score"
    }
  }
}
