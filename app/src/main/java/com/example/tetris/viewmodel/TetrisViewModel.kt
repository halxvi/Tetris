package com.example.tetris.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.model.Tetris
import java.util.*
import kotlin.concurrent.schedule

class TetrisViewModel(
  private val tetris: Tetris
) : ViewModel() {
  var fields: MutableLiveData<Array<Array<Int>>> =
    MutableLiveData(Array(22) { Array<Int>(12) { 0 } })
  var nextBlocks: MutableLiveData<List<Int>> = MutableLiveData(List(3) { 0 })
  var score: MutableLiveData<Int> = MutableLiveData(0)
  var gameover: Boolean = false
  private var sleepTime: Long = 1
  private var timer: Timer = Timer()
  private val timerTask: TimerTask.() -> Unit = {
    if (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
      tetris.moveBlock()
      Log.i("Tetris:moveBlock", "moveBlock")
    }
    if (!tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
      Log.i("Tetris:findIndex", "${tetris.findErasableBlocksIndex()}")
      tetris.eraseBlocks()
      Log.i("Tetris:EraseBlocks", "EraseBlocks")
      tetris.addBlock()
      Log.i("Tetris:AddBlock", "AddBlock")
    }
    fetchState()
    Log.i("Tetris:canMoveBlock", "${tetris.canMoveBlock(tetris.selectedBlock.coordinates)}")
    Log.i("Tetris:isGameover", "${tetris.isGameover()}")
    gameover = tetris.isGameover()
    if (gameover) endGame()
    checkCounter()
    Thread.sleep(sleepTime)
  }
  private var counter: Int = 0
  private var isSpeedUp: Boolean = false

  fun startGame() {
    timer.schedule(0, 1000, timerTask)
    tetris.startGame()
    Log.i("Tetris:Start", "GameStart")
  }

  private fun endGame() {
    timer.cancel()
    Log.i("Tetris:End", "GameEnd")
  }

  private fun checkCounter() {
    if (counter == 1000) {
      if (sleepTime > 100) sleepTime -= 1
      counter = 0
    } else {
      counter += 1
    }
  }

  private fun fetchState() {
    Log.i("Tetris:NextBlocks", "${tetris.nextBlocks}")
    Log.i("Tetris:Score", "${tetris.score}")
    fields.postValue(tetris.combineAllBlocks())
    nextBlocks.postValue(tetris.nextBlocks)
    score.postValue(tetris.score)
  }

  fun flickToRight() {
    tetris.flickToRight()
    Log.i("Tetris:FlickToRight", "FlickToRight")
  }

  fun flickToLeft() {
    tetris.flickToLeft()
    Log.i("Tetris:FlickToLeft", "FlickToLeft")
  }

  fun rotate() {
    tetris.rotate()
    Log.i("Tetris:Rotate", "Rotate")
  }

  fun setSpeedUp() {
    isSpeedUp = true
    while (isSpeedUp) {
      Thread.sleep(sleepTime)
      tetris.moveBlock()
    }
    Log.i("Tetris:SpeedUp", "SpeedUp")
  }

  fun unsetSpeed() {
    if (isSpeedUp) isSpeedUp = false
  }
}