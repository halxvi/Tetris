package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val tetris = Tetris()
    val handler = Handler()
    //var gameSpeed:Double = 1000.0
    timer(initialDelay = 2000, period = 1000) {
      updateField(handler, tetris.fields)
      updateScore(handler, tetris.score)
    }
//    fun setTimer() {
//      timer.cancel()
//      timer.schedule() {
//        updateField(tetris.fields)
//        updateScore(tetris.score)
//      }
//    }
  }

  private fun updateField(handler: Handler, fields: Array<Array<Int>>) {
    handler.post {
      fields.forEachIndexed { firstIndex, array ->
        if (firstIndex > 1) {
          array.forEachIndexed { lastIndex, block_num ->
            val blockArrayIndex = (firstIndex - 2 ) * 10 + (lastIndex + 1)
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
              else -> {
                blockId.setImageResource(R.drawable.block_background)
              }
            }
          }
        }
      }
    }
  }

  private fun updateScore(handler: Handler, score: Int) {
    handler.post {
      scoreView.text = "Your Score:$score"
    }
  }
}
