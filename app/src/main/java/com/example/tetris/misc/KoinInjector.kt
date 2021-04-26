package com.example.tetris.misc

import com.example.tetris.model.*
import com.example.tetris.field.Field
import com.example.tetris.viewmodel.TetrisViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { Field() }
  single { TetrisModel(get()) }
  viewModel { TetrisViewModel(get()) }
}