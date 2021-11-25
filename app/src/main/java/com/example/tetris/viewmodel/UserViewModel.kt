package com.example.tetris.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
  var isLogging = false
  var submittedScore = MutableLiveData(false)
  var emailAddress = ""
}