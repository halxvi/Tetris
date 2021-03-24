package com.example.tetris.model

import androidx.lifecycle.MutableLiveData

class TetrisViewModelRepository : TetrisViewModelRepositoryInterface {
  override var fields: MutableLiveData<Array<Array<Int>>> =
    MutableLiveData(Array(22) { Array<Int>(12) { 0 } })
  override var nextBlocks: MutableLiveData<MutableList<Int>> = MutableLiveData(MutableList(3) { 0 })
  override var score: MutableLiveData<Int> = MutableLiveData(0)
  override var gameover: MutableLiveData<Boolean> = MutableLiveData(false)
}