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
    MutableLiveData(Array(25) { Array(12) { 0 } })
  var nextBlocks: MutableLiveData<List<Int>> = MutableLiveData(List(3) { 0 })
  var score: MutableLiveData<Int> = MutableLiveData(0)
  var level: MutableLiveData<Int> = MutableLiveData(0)
  var gameover: MutableLiveData<Boolean> = MutableLiveData(false)
  private var gameSpeed: Long = 1000
  private val gameSpeedThreshold: Int = 500
  private var mainTimer: Timer = Timer(true)
  private lateinit var gameSpeedTimer: Timer

  fun startGame() {
    startGameSpeedTimer()
    tetris.startGame()
    Log.i("Tetris:Start", "GameStart")
  }

  private fun endGame() {
    deleteAllTimer()
    gameover.postValue(true)
    Log.i("Tetris:End", "GameEnd")
  }

  private fun calGameSpeed(): Long {
    return if(gameSpeed > gameSpeedThreshold) gameSpeed.minus(1) else gameSpeed
  }

  private fun fetchState() {
    fields.postValue(tetris.combineAllBlocks())
    nextBlocks.postValue(tetris.nextBlocks)
    score.postValue(tetris.score)
    level.postValue(tetris.level)
  }

  fun flickToRight() {
    tetris.flickToRight()
  }

  fun flickToLeft() {
    tetris.flickToLeft()
  }

  fun rotate() {
    tetris.rotate()
  }

  fun fallDownBlock() {
    tetris.fallDownBlock()
  }

  private fun startMainTimer(){
    val task: TimerTask.() -> Unit = {
      if (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
        tetris.moveBlock()
      }
      if (!tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
        tetris.addBlock()
      }
      tetris.eraseBlocks()
      fetchState()
      if (tetris.isGameover()) endGame()
    }
    mainTimer = Timer(true)
    mainTimer.scheduleAtFixedRate(0, gameSpeed, task)
    Log.i("Tetris:TimerM", "GameStart")
  }

  fun startGameSpeedTimer(){
    val task: TimerTask.() -> Unit = {
      gameSpeed = calGameSpeed()
      mainTimer.cancel()
      mainTimer = Timer(true)
      startMainTimer()
      Log.i("Tetris:TimerM", "A")
    }
    gameSpeedTimer = Timer(true)
    gameSpeedTimer.scheduleAtFixedRate(0, 1000, task)
    Log.i("Tetris:TimerGS", "GameStart")
  }

  fun deleteAllTimer(){
    mainTimer.cancel()
    mainTimer = Timer(true)
    gameSpeedTimer.cancel()
    gameSpeedTimer = Timer(true)
  }

  fun restart(){
    deleteAllTimer()
    fields.postValue(Array(25) { Array(12) { 0 } })
    nextBlocks.postValue(List(3) { 0 })
    score.postValue(0)
    gameover.postValue(false)
    gameSpeed = 1000
    tetris.restart()
    startGame()
  }
}