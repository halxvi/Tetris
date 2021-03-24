package com.example.tetris.model

import androidx.lifecycle.MutableLiveData

interface TetrisViewModelRepositoryInterface {
  var fields: MutableLiveData<Array<Array<Int>>>
  var nextBlocks: MutableLiveData<MutableList<Int>>
  var score: MutableLiveData<Int>
  var gameover: MutableLiveData<Boolean>
}