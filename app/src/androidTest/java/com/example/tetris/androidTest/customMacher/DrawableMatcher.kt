package com.example.tetris.androidTest.customMacher

import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasDrawable(expectedId: Int): Matcher<View> {
  return object : TypeSafeMatcher<View>() {
    override fun matchesSafely(view: View): Boolean {
      if (view is ImageView) {
        return view.tag == expectedId
      }
      return false
    }

    override fun describeTo(description: Description) {
      description.appendText("has drawable $expectedId")
    }
  }
}