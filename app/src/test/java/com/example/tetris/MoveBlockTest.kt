package com.example.tetris

import com.example.tetris.model.TetrisModel
import com.example.tetris.repository.TetrisModelRepository
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
    model.nextBlocks = MutableList<Int>(3) { 4 }
    model.addBlock()
    model.moveBlock()
    var expectedArray: Array<Array<Int>> = Array(23) { Array<Int>(10) { 0 } }
    for (i in 0..9) expectedArray[22][i] = 16
    for (i in 0..1) {
      expectedArray[1][i] = 11
      expectedArray[2][i + 1] = 11
    }
    assertArrayEquals(expectedArray, model.fields)
  }
}
