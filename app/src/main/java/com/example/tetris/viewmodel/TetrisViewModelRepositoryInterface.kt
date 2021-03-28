package com.example.tetris.viewmodel

import androidx.lifecycle.MutableLiveData

interface TetrisViewModelRepositoryInterface {
  var fields: MutableLiveData<Array<Array<Int>>>
  var nextBlocks: MutableLiveData<Array<Int>>
  var score: MutableLiveData<Int>
  var gameover: MutableLiveData<Boolean>
}