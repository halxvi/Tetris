package com.example.tetris.model

import com.example.tetris.misc.Score
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repository : IRepository {
  private val database: FirebaseDatabase = Firebase.database

  override fun fetchScore(): Task<DataSnapshot> {
    return database.getReference("score").get()
  }

  override fun setScore(score: Score) {
    database.getReference("score").push().setValue(score)
  }
}