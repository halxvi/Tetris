package com.example.tetris.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.model.TetrisModel

class TetrisViewModel(
  repo: TetrisViewModelRepository,
  val model: TetrisModel
) : ViewModel() {
  var fields: MutableLiveData<Array<Array<Int>>> = repo.fields
  var nextBlocks: MutableLiveData<Array<Int>> = repo.nextBlocks
  var score: MutableLiveData<Int> = repo.score
  var gameover: MutableLiveData<Boolean> = repo.gameover

  fun flickBlock(direction: String) {}

  fun onSpeedUp() {
    model.onSpeedUp()
  }

  fun rotateBlock() {}
}