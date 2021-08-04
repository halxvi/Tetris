package com.example.tetris.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

interface IRepository {
  val database: FirebaseDatabase

  fun reference(referenceName:String): DatabaseReference
  fun write(data: String)
  fun read()
}