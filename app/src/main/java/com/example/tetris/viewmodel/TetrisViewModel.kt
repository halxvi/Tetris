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
    MutableLiveData(Array(22) { Array(12) { 0 } })
  var nextBlocks: MutableLiveData<List<Int>> = MutableLiveData(List(3) { 0 })
  var score: MutableLiveData<Int> = MutableLiveData(0)
  private var gameover: Boolean = false
  private var gameSpeed: Long = 1000
  private var gameSpeedCounter: Int = 0
  private var gameSpeedThreshold: Int = 500
  var isSpeedUp: Boolean = false
  private var timer: Timer = Timer()

  fun startGame() {
    startTimer(gameSpeed)
    tetris.startGame()
    Log.i("Tetris:Start", "GameStart")
  }

  private fun endGame() {
    deleteTimer()
    Log.i("Tetris:End", "GameEnd")
  }

  private fun calGameSpeed() {
    Log.i("Tetris:GameSpeedCounter", "${this.gameSpeedCounter}")
    if (gameSpeedCounter == 1000) {
      if (gameSpeed > gameSpeedThreshold) gameSpeed -= 1
      deleteTimer()
      startTimer(gameSpeed)
      gameSpeedCounter = 0
    } else {
      gameSpeedCounter += 1
    }
    Log.i("Tetris:gameSpeed", "$gameSpeed")
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
    if(isSpeedUp) return
    isSpeedUp = true
    deleteTimer()
    startTimer(if(gameSpeed > gameSpeedThreshold) gameSpeed - gameSpeedThreshold else gameSpeed)
    Log.i("Tetris:SetSpeedUp", "SetSpeedUp")
  }

  fun setSpeedDown() {
    if (!isSpeedUp) return
    isSpeedUp = false
    deleteTimer()
    startTimer(gameSpeed)
    Log.i("Tetris:SetSpeedDown", "SetSpeedDown")
  }

  private fun startTimer(speed:Long){
    val task: TimerTask.() -> Unit = {
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
      calGameSpeed()
    }
    timer = Timer()
    timer.schedule(0, speed, task)
    Log.i("Tetris:StartTimer", "StartTimer")
  }

  private fun deleteTimer(){
    timer.cancel()
    Log.i("Tetris:DeleteTimer", "DeleteTimer")
  }
}