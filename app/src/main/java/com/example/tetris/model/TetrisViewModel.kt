package com.example.tetris.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetris.repository.TetrisViewModelRepository

class TetrisViewModel(repo: TetrisViewModelRepository) : ViewModel() {
  var fields: MutableLiveData<Array<Array<Int>>> = repo.fields
  var nextBlocks: MutableLiveData<MutableList<Int>> = repo.nextBlocks
  var score: MutableLiveData<Int> = repo.score
  var gameover: MutableLiveData<Boolean> = repo.gameover

  fun flickBlock(direction: String) {}

  fun onSpeedUp() {}

  fun rotateBlock() {}
}