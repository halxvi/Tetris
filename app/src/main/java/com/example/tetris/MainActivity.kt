package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tetris = Tetris()
        updateField(tetris.fields)
    }

    private fun updateField(fields:Array<Array<Int>>){
        fields.forEachIndexed { firstIndex, array->
            array.forEachIndexed { lastIndex, block_num->
                val blockArrayIndex = firstIndex * 10 + ( lastIndex + 1 )
                val resourceId = resources.getIdentifier("block_$blockArrayIndex", "id", packageName)
                val blockId:ImageView = findViewById(resourceId)
                when (block_num) {
                    1 -> {
                        blockId.setImageResource(R.drawable.blue_block)
                    }
                    2 -> {
                        blockId.setImageResource(R.drawable.red_block)
                    }
                    3 -> {
                        blockId.setImageResource(R.drawable.green_block)
                    }
                    4 -> {
                        blockId.setImageResource(R.drawable.orange_block)
                    }
                    5 -> {
                        blockId.setImageResource(R.drawable.yellow_block)
                    }
                    6 -> {
                        blockId.setImageResource(R.drawable.lightblue_block)
                    }
                    7 -> {
                        blockId.setImageResource(R.drawable.purple_block)
                    }
                    else -> {
                        blockId.setImageResource(R.drawable.block_background)
                    }
                }
            }
        }
    }
}
