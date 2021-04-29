package com.example.tetris.misc

import com.example.tetris.model.Field
import com.example.tetris.model.Tetris
import com.example.tetris.viewmodel.TetrisViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { Field() }
  single { Tetris(get()) }
  viewModel { TetrisViewModel(get()) }
}