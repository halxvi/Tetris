package com.example.tetris.viewmodel

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
  private val gameSpeedThreshold: Long = 500
  private lateinit var timer: Timer

  fun startGame() {
    startTimer()
    tetris.startGame()
  }

  private fun endGame() {
    deleteTimer()
    gameover.postValue(true)
  }

  private fun calGameSpeed(): Long {
    if((tetris.level + 1) % 2 == 0) {
      return if(gameSpeed > gameSpeedThreshold) gameSpeed.minus(1) else gameSpeed
    }
    return gameSpeed
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

  fun startTimer(){
    val task: TimerTask.() -> Unit = {
      if (tetris.isGameover()) endGame()
      if (tetris.canMoveBlock(tetris.selectedBlock.coordinates)) {
        tetris.moveBlock()
      } else {
        tetris.addBlock()
      }
      tetris.eraseBlocks()
      fetchState()
      if(gameSpeed != calGameSpeed()){
        gameSpeed = calGameSpeed()
        deleteTimer()
        startTimer()
      }
    }
    timer = Timer()
    timer.scheduleAtFixedRate(0, gameSpeed, task)
  }

  fun deleteTimer(){
    timer.cancel()
    timer = Timer()
  }

  fun restart(){
    fields.postValue(Array(25) { Array(12) { 0 } })
    nextBlocks.postValue(List(3) { 0 })
    score.postValue(0)
    gameover.postValue(false)
    gameSpeed = 1000
    tetris.restart()
    startGame()
  }
}