package com.example.tetris

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.tetris.view.GameoverFragment
import com.example.tetris.view.TetrisFragment
import com.example.tetris.viewmodel.TetrisViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
  private lateinit var mDetector: GestureDetectorCompat
  private val viewModel: TetrisViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mDetector = GestureDetectorCompat(this, MyGestureListener(viewModel))
    val tetirsFragment = TetrisFragment()
    val gameoverFragment = GameoverFragment()
    if(savedInstanceState == null) {
      val fragmentManager = supportFragmentManager
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.add(R.id.fragment_container, tetirsFragment, "Tetris")
      fragmentTransaction.add(R.id.fragment_container, gameoverFragment, "Gameover")
      fragmentTransaction.detach(gameoverFragment)
      fragmentTransaction.commit()
    }
    viewModel.startGame()
  }

  override fun onRestart() {
    super.onRestart()
    viewModel.startTimer()
  }

  override fun onStop() {
    super.onStop()
    viewModel.deleteTimer()
  }

  override fun onTouchEvent(e: MotionEvent): Boolean {
    mDetector.onTouchEvent(e)
    return super.onTouchEvent(e)
  }

  private class MyGestureListener(val viewModel: TetrisViewModel) :
    GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
      e1: MotionEvent,
      e2: MotionEvent,
      velocityX: Float,
      velocityY: Float
    ): Boolean {
      val calculateX = e1.x - e2.x
      if (calculateX < 0) {
        viewModel.flickToRight()
      } else {
        viewModel.flickToLeft()
      }
      return super.onFling(e1, e2, velocityX, velocityY)
    }

    override fun onDown(e: MotionEvent?): Boolean {
      return true
    }

    override fun onLongPress(e: MotionEvent) {
      viewModel.fallDownBlock()
      super.onLongPress(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
      viewModel.rotate()
      return super.onDoubleTap(e)
    }
  }
}
