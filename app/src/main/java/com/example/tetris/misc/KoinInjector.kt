package com.example.tetris.misc

import com.example.tetris.model.Tetris
import com.example.tetris.viewmodel.TetrisViewModel
import com.example.tetris.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { Tetris() }

  viewModel { TetrisViewModel(get()) }
  viewModel { UserViewModel() }
}