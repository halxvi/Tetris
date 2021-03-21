package com.example.tetris

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.tetris.model.TetrisViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity() : AppCompatActivity() {
  private lateinit var mDetector: GestureDetectorCompat
  private val viewModel: TetrisViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mDetector = GestureDetectorCompat(this, MyGestureListener(this.viewModel))
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    mDetector.onTouchEvent(event)
    return super.onTouchEvent(event)
  }

  private class MyGestureListener(val viewModel: TetrisViewModel) :
    GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
      e1: MotionEvent?,
      e2: MotionEvent?,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      val calculateX = e1!!.x - e2!!.x
      if (calculateX < 0) {
        viewModel.flickBlock("right")
      } else {
        viewModel.flickBlock("left")
      }
      return super.onFling(e1, e2, velocityX, velocityY)
    }

    override fun onLongPress(e: MotionEvent?) {
      viewModel.onSpeedUp()
      super.onLongPress(e)
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
      viewModel.rotateBlock()
      return super.onDoubleTap(e)
    }
  }
}
