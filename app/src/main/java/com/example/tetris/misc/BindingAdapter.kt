package com.example.tetris.misc

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.core.content.ContextCompat
import com.example.tetris.R

private val drawableType = mapOf(
  1 to R.drawable.lightblue_block,
  2 to R.drawable.yellow_block,
  3 to R.drawable.red_block,
  4 to R.drawable.green_block,
  5 to R.drawable.blue_block,
  6 to R.drawable.orange_block,
  7 to R.drawable.purple_block,
  8 to R.drawable.target_block_background
)

@BindingAdapter("app:blockImage")
fun blockImage(view: ImageView, blockType: Int) {
  view.setImageDrawable(
    ContextCompat.getDrawable(
      view.context,
      drawableType[blockType] ?: R.drawable.block_background
    )
  )
  view.tag = drawableType[blockType] ?: R.drawable.block_background
}
