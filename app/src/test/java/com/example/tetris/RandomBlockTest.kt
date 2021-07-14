package com.example.tetris

import com.example.tetris.block.BlockInterface
import com.example.tetris.block.StraightBlock
import com.example.tetris.model.Tetris
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.random.Random

class RandomBlockTest : KoinTest {
  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      module {
        single { (
                   selectedBlocks: BlockInterface,
                   blocks: Array<Array<Int>>,
                   nextBlocks: MutableList<Int>,
                   random: Random
                 )
          ->
          Tetris(
            selectedBlocks,
            blocks,
            nextBlocks,
            random
          )
        }
      }
    )
  }

  @Test
  fun checkInitNextBlocks() {
    val tetris: Tetris by inject {
      parametersOf(
        StraightBlock(),
        Array(24) { Array(12) { 0 } },
        mutableListOf(0, 0, 0),
        Random(0)
      )
    }

    val expectedNextBlocks = mutableListOf(0, 0, 0)
    val r = Random(0)
    for (index in expectedNextBlocks.indices) {
      expectedNextBlocks[index] = r.nextInt(1, 7)
    }
    assertEquals(expectedNextBlocks, tetris.nextBlocks)
  }
}