package com.example.tetris.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repository(
  override val database: FirebaseDatabase = Firebase.database
  ) : IRepository {
  private val ref = database.getReference("user_name")

  override fun reference(referenceName:String):DatabaseReference {
    return database.getReference(referenceName)
  }

  override fun write(data:String) {
    ref.setValue(data)
  }

  override fun read() {
    TODO("Not yet implemented")
  }
}