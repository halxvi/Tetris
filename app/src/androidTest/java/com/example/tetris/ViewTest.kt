package com.example.tetris

import com.example.tetris.androidTest.customMacher.hasDrawable
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewTest {
  @get:Rule
  var activityScenarioRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

  @Test
  fun init() {
    for (i in 1..200) {
      onView(withResourceName("block_$i"))
        .check(matches(hasDrawable(R.drawable.block_background)))
    }
    for (n in 1..3) {
      onView(withResourceName("nextBlockView_$n"))
        .check(matches(hasDrawable(R.drawable.block_background)))
    }
    onView(withId(R.id.scoreView))
      .check(matches(withText("Score:0")))
  }
}