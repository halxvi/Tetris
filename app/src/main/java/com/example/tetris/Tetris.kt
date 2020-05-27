package com.example.tetris

import kotlin.random.Random

class Tetris {
  val fields: Array<Array<Int>> = Array(20){ Array<Int>( 10){ 0 };}
  val score = 0;
  private val straightBlock = Array(4){ 1 }
  private val squareBlock = arrayOf(arrayOf(2,2), arrayOf(2,2))
  private val zBlock1 = arrayOf(arrayOf(0,3,3), arrayOf(3,3,0))
  private val zBlock2 = arrayOf(arrayOf(4,4,0), arrayOf(0,4,4))
  private val lBlock1 = arrayOf(arrayOf(5,0,0), Array(3){ 5 })
  private val lBlock2 = arrayOf(arrayOf(0,0,6), Array(2){ 6 })
  private val tBlock = arrayOf(arrayOf(0,7,0), Array(3){ 7 })
  private val blocks = arrayOf(
    straightBlock,
    squareBlock,
    zBlock1,
    zBlock2,
    lBlock1,
    lBlock2,
    tBlock
  );
}