package com.example.tetris

import android.app.Application
import com.example.tetris.misc.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TetrisApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@TetrisApplication)
      modules(appModule)
    }
  }
}