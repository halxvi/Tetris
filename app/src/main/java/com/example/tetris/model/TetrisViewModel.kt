package com.example.tetris.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TetrisViewModel(repo: TetrisViewModelRepository, model: TetrisModel) : ViewModel() {
  private val model = model
  var fields: MutableLiveData<Array<Array<Int>>> = repo.fields
  var nextBlocks: MutableLiveData<MutableList<Int>> = repo.nextBlocks
  var score: MutableLiveData<Int> = repo.score
  var gameover: MutableLiveData<Boolean> = repo.gameover

  fun flickBlock(direction: String) {}

  fun onSpeedUp() {
    model.onSpeedUp()
  }

  fun rotateBlock() {}
}