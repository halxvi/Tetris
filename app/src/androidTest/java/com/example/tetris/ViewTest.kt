package com.example.tetris

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {
  @get:Rule
  var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

  @Test
  fun useAppContext() {

  }
}
