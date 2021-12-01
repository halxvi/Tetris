package com.example.tetris

import android.app.Application
import com.example.tetris.misc.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TetrisApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
      androidContext(this@TetrisApplication)
      modules(appModule)
    }
  }
}