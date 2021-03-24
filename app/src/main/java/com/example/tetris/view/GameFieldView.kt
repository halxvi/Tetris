package com.example.tetris.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.example.tetris.R

class GameFieldView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
  private lateinit var field: Array<Array<Int>>
  private var mRect = Rect()
  private var fillPaint = Paint().apply {
    style = Paint.Style.FILL
  }

  @RequiresApi(Build.VERSION_CODES.M)
  private var strokePaint = Paint().apply {
    color = context.getColor(R.color.colorBlockStroke)
    strokeWidth = 1F
    style = Paint.Style.STROKE
  }
  private val colorType = mapOf(
    1 to R.color.colorBlockLightBlue,
    2 to R.color.colorBlockYellow,
    3 to R.color.colorBlockRed,
    4 to R.color.colorBlockGreen,
    5 to R.color.colorBlockBlue,
    6 to R.color.colorBlockOrange,
    7 to R.color.colorBlockPurple,
    8 to R.color.colorBlockBackground
  )

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    mRect.set(0, 0, width / 10, height / 20)
    var offsetHeight = 0
    for (y in 0..19) {
      var offsetWidth = 0
      for (x in 0..9) {
        mRect.offsetTo(offsetWidth, offsetHeight)
        canvas?.drawRect(
          mRect,
          fillPaint.apply {
            color = context.getColor(colorType[field[y][x]] ?: R.color.colorBlockBackground)
          })
        canvas?.drawRect(
          mRect,
          strokePaint
        )
        offsetWidth += width / 10
      }
      offsetHeight += height / 20
    }
  }

  fun setField(field: Array<Array<Int>>) {
    this.field = field
  }
}