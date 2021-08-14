package com.example.tetris.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repository(
  private val database: FirebaseDatabase = Firebase.database,
  private var ref: DatabaseReference = database.getReference("score")
) : IRepository {

  override fun setReference(referenceName:String) {
    ref = database.getReference(referenceName)
  }

  override fun setReferenceChild(referenceName:String) {
    ref = ref.child(referenceName)
  }

  override fun push(){
    ref = ref.push()
  }

  override fun setValue(data:Any) {
    ref.setValue(data)
  }

  override fun read() {
    TODO("Not yet implemented")
  }
}