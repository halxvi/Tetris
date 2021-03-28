package com.example.tetris.viewmodel

import androidx.lifecycle.MutableLiveData

class TetrisViewModelRepository : TetrisViewModelRepositoryInterface {
  override var fields: MutableLiveData<Array<Array<Int>>> =
    MutableLiveData(Array(22) { Array<Int>(12) { 0 } })
  override var nextBlocks: MutableLiveData<Array<Int>> = MutableLiveData(Array(3) { 0 })
  override var score: MutableLiveData<Int> = MutableLiveData(0)
  override var gameover: MutableLiveData<Boolean> = MutableLiveData(false)
}