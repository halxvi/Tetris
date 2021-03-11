package com.example.tetris

import org.junit.Test

import org.junit.Assert.*

class MoveBlockTest {
    @Test
    fun moveBlock() {
        val tetris = Tetris()
        //zBlock
        tetris.nextBlocks = MutableList<Int>(3) { 4 }
        tetris.addBlock()
        tetris.moveBlock()
        var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
        for (i in 0..9) expectedArray[22][i] = 16
        for (i in 0..1) {
            expectedArray[1][i] = 11
            expectedArray[2][i + 1] = 11
        }
        assertArrayEquals(expectedArray, tetris.fields)
    }
}
