package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
  private var timer = timer(period = 1000.toLong()) {
    if (tetris.getGameOver()) checkGameOver()
    updateField(handler, tetris.getField())
    updateHeader(handler, tetris.getScore(), tetris.nextBlocks)
  }
  private val drawableType = mapOf(
    1 to R.drawable.lightblue_block,
    2 to R.drawable.yellow_block,
    3 to R.drawable.red_block,
    4 to R.drawable.green_block,
    5 to R.drawable.blue_block,
    6 to R.drawable.orange_block,
    7 to R.drawable.purple_block,
    8 to R.drawable.target_block_background
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mDetector = GestureDetectorCompat(this, MyGestureListener(this.tetris))
    restartButton.setOnClickListener {
      timer = timer(period = tetris.getGameSpeed().toLong()) {
        if (tetris.getGameOver()) checkGameOver()
        updateField(handler, tetris.getField())
        updateHeader(handler, tetris.getScore(), tetris.nextBlocks)
      }
      gameOverLayout.visibility = View.INVISIBLE
    }
    gameOverLayout.visibility = View.INVISIBLE
    updateField(handler, tetris.getField())
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

    override fun onDoubleTap(e: MotionEvent?): Boolean {
      tetris.rotateBlock()
      return super.onDoubleTap(e)
    }
  }

  private fun checkGameOver() {
    timer.cancel()
    handler.post {
      gameOverLayout.visibility = View.VISIBLE
      gameOverScore.text = tetris.getScore().toString()
    }
  }

  private fun updateField(handler: Handler, fields: Array<Array<Int>>) {
    handler.post {
      fields.forEachIndexed { n, array ->
        if (n in 2..21) {
          array.forEachIndexed { m, blockNumber: Int ->
            val i = (n - 2) * 10 + (m + 1)
            val resourceId = resources.getIdentifier("block_$i", "id", packageName)
            val imageView: ImageView = findViewById(resourceId)
            imageView.setImageResource(R.drawable.block_background)
            imageView.tag = R.drawable.block_background
          }
        }
      }
    }
  }

  private fun updateHeader(handler: Handler, score: Int, nextBlocks: MutableList<Int>) {
    handler.post {
      for (i in 1..3) {
        val resourceId = resources.getIdentifier("nextBlockView_$i", "id", packageName)
        val imageView: ImageView = findViewById(resourceId)
        imageView.setImageResource(R.drawable.block_background)
        imageView.tag = R.drawable.block_background
      }
      scoreView.text = "Score:$score"
    }
  }
}
