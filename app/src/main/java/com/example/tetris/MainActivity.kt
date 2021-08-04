package com.example.tetris

import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.example.tetris.view.GameoverFragment
import com.example.tetris.view.RankingFragment
import com.example.tetris.view.RankingSubmitFormFragment
import com.example.tetris.view.TetrisFragment
import com.example.tetris.viewmodel.TetrisViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
  private lateinit var mDetector: GestureDetectorCompat
  private val viewModel: TetrisViewModel by viewModel()

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mDetector = GestureDetectorCompat(this, MyGestureListener(viewModel))
    val tetrisFragment = TetrisFragment()
    val gameoverFragment = GameoverFragment()
    val rankingFragment = RankingFragment()
    val rankingSubmitFormFragment = RankingSubmitFormFragment()
    if(savedInstanceState == null) {
      val fragmentManager = supportFragmentManager
      val fragmentTransaction = fragmentManager.beginTransaction()
      fragmentTransaction.add(R.id.fragment_container, tetrisFragment, "Tetris")
      fragmentTransaction.add(R.id.fragment_container, gameoverFragment, "Gameover")
      fragmentTransaction.detach(gameoverFragment)
      fragmentTransaction.add(R.id.fragment_container, rankingFragment,"Ranking")
      fragmentTransaction.detach(rankingFragment)
      fragmentTransaction.add(R.id.fragment_container, rankingSubmitFormFragment,"RankingSubmitForm")
      fragmentTransaction.detach(rankingSubmitFormFragment)
      fragmentTransaction.commit()
    }
    viewModel.startGame()
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
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
