package com.example.tetris.model

import com.example.tetris.misc.Score
import com.google.firebase.database.DataSnapshot

interface IRepository {
  fun setScore(score: Score)
  fun fetchScore(): DataSnapshot?
}