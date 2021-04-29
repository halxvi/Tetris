package com.example.tetris.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.model.Tetris

class TetrisViewModel(
  private val tetris: Tetris
) : ViewModel() {
  var fields: MutableLiveData<Array<Array<Int>>> = MutableLiveData(Array(22) { Array<Int>(12) { 0 } })
  var nextBlocks: MutableLiveData<Array<Int>> = MutableLiveData(Array(3) { 0 })
  var score: MutableLiveData<Int> = MutableLiveData(0)
  var gameover: MutableLiveData<Boolean> = MutableLiveData(false)

  fun flickBlock(direction: String) {}

  fun onSpeedUp() {
    tetris.onSpeedUp()
  }

  fun rotateBlock() {}
}