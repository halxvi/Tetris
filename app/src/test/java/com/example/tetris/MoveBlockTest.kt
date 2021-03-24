package com.example.tetris

import com.example.tetris.model.TetrisModel
import com.example.tetris.model.TetrisModelRepository
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class MoveBlockTest : KoinTest {

  private val model: TetrisModel by inject()

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { TetrisModelRepository() }
        factory { TetrisModel(get()) }
      })
  }

  @Test
  fun moveZBlock() {
    model.addZBlock()
    model.moveBlock()
    var expectedArray: Array<Array<Int>> = Array(22) { Array<Int>(12) { 0 } }
    for (i in 5..6) {
      expectedArray[0][i] = 3
      expectedArray[1][i + 1] = 3
    }
    expectedArray = addWallToArray(expectedArray)
    assertArrayEquals(expectedArray, model.fields)
  }

  private fun addWallToArray(array:Array<Array<Int>>): Array<Array<Int>> {
    for (n in 0..21) array[n][0] = 16
    for (n in 0..21) array[n][11] = 16
    for (i in 2..10) array[21][i] = 16
    return array
  }
}
