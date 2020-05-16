package com.example.tetris

import kotlin.random.Random

class Tetris {
    init {
        val fields = Array<int>(10){ Array<int>(20){ Random.nextInt(0,7) };}
        val straightBlock = Array<int>(4){ 1 }
        val squareBlock = arrayOf(arrayOf(2,2), arrayOf(2,2))
        val zBlock1 = arrayOf(arrayOf(0,3,3), arrayOf(3,3,0))
        val zBlock2 = arrayOf(arrayOf(4,4,0), arrayOf(0,4,4))
        val lBlock1 = arrayOf(arrayOf(5,0,0), Array(3){ 5 })
        val lBlock2 = arrayOf(arrayOf(0,0,6), Array(2){ 6 })
        val tBlock = arrayOf(arrayOf(0,7,0), Array(3){ 7 })
        val blocks = arrayOf(
            straightBlock,
            squareBlock,
            zBlock1,
            zBlock2,
            lBlock1,
            lBlock2,
            tBlock
        );
    }
}