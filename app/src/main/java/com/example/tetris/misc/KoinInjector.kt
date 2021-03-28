package com.example.tetris.misc

import com.example.tetris.model.*
import com.example.tetris.model.field.Field
import com.example.tetris.viewmodel.TetrisViewModel
import com.example.tetris.viewmodel.TetrisViewModelRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { TetrisModelRepository() }
  single { TetrisViewModelRepository() }
  single { Field() }
  single { TetrisModel(get(), get()) }
  viewModel { TetrisViewModel(get(), get()) }
}