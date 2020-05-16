package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    init {
        val tetris = Tetris()
        val blockImgs = arrayOf(
            "@drawable/lightblue_block.xml",
            "@drawable/yellow_block.xml",
            "@drawable/green_block.xml",
            "@drawable/red_block.xml",
            "@drawable/blue_block.xml",
            "@drawable/orange_block.xml",
            "@drawable/purple_block.xml"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
