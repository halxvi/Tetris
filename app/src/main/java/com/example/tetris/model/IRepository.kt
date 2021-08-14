package com.example.tetris.model

interface IRepository {
  fun setReference(referenceName:String)
  fun setReferenceChild(referenceName:String)
  fun setValue(data:Any)
  fun push()
  fun read()
}