package com.example.tetris.repository

import androidx.lifecycle.MutableLiveData
import com.example.tetris.interfaces.TetrisViewModelRepositoryInterface

class TetrisViewModelRepository : TetrisViewModelRepositoryInterface {
  override var fields: MutableLiveData<Array<Array<Int>>> =
    MutableLiveData(Array(23) { Array<Int>(10) { 0 } })
  override var nextBlocks: MutableLiveData<MutableList<Int>> = MutableLiveData(MutableList(3) { 0 })
  override var score: MutableLiveData<Int> = MutableLiveData(0)
  override var gameover: MutableLiveData<Boolean> = MutableLiveData(false)
}