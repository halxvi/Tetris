package com.example.tetris.misc

import com.example.tetris.repository.TetrisModelRepository
import com.example.tetris.repository.TetrisViewModelRepository
import com.example.tetris.model.TetrisModel
import com.example.tetris.model.TetrisViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { TetrisModelRepository() }
  single { TetrisViewModelRepository() }
  factory { TetrisModel(get()) }
  viewModel { TetrisViewModel(get()) }
}