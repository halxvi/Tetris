package com.example.tetris.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.model.Tetris
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class TetrisViewModel(
  private val tetris: Tetris
) : ViewModel() {
  var fields: MutableLiveData<Array<Array<Int>>> =
    MutableLiveData(Array(24) { Array(12) { 0 } })
  var nextBlocks: MutableLiveData<List<Int>> = MutableLiveData(List(3) { 0 })
  var score: MutableLiveData<Int> = MutableLiveData(0)
  var gameover: MutableLiveData<Boolean> = MutableLiveData(false)
  private var gameSpeed: Long = 1000
  private var gameSpeedCounter: Int = 0
  private val gameSpeedThreshold: Int = 500
  private lateinit var timer: Timer

  fun startGame() {
    startTimer()
    tetris.startGame()
    Log.i("Tetris:Start", "GameStart")
  }

  private fun endGame() {
    deleteTimer()
    gameover.postValue(true)
    Log.i("Tetris:End", "GameEnd")
  }

  private fun calGameSpeed() {
    Log.i("Tetris:GameSpeedCounter", "${this.gameSpeedCounter}")
    if (gameSpeedCounter == 1000) {
      if (gameSpeed > gameSpeedThreshold) gameSpeed -= 1
      deleteTimer()
      startTimer()
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

  fun fallDownBlock() {
    tetris.fallDownBlock()
  }

  fun startTimer(){
    val task: TimerTask.() -> Unit = {
      if (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
        tetris.moveBlock()
        Log.i("Tetris:moveBlock", "moveBlock")
      }
      if (!tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
        tetris.addBlock()
        Log.i("Tetris:AddBlock", "AddBlock")
      }
      tetris.eraseBlocks()
      Log.i("Tetris:EraseBlocks", "EraseBlocks")
      fetchState()
      Log.i("Tetris:canMoveBlock", "${tetris.canMoveBlock(tetris.selectedBlock.coordinates)}")
      Log.i("Tetris:isGameover", "${tetris.isGameover()}")
      if (tetris.isGameover()) endGame()
      calGameSpeed()
    }
    timer = Timer()
    timer.scheduleAtFixedRate(0, gameSpeed, task)
    Log.i("Tetris:StartTimer", "StartTimer")
  }

  fun deleteTimer(){
    timer.cancel()
    timer = Timer()
    Log.i("Tetris:DeleteTimer", "DeleteTimer")
  }

  fun restart(){
    deleteTimer()
    fields.postValue(Array(24) { Array(12) { 0 } })
    nextBlocks.postValue(List(3) { 0 })
    score.postValue(0)
    gameover.postValue(false)
    gameSpeed = 1000
    gameSpeedCounter = 0
    tetris.restart()
    startGame()
  }
}